package com.learningportal.documentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for validating documentation freshness and completeness.
 * 
 * This service provides comprehensive validation of documentation including:
 * - Checking for outdated documentation files
 * - Validating documentation completeness against code changes
 * - Ensuring all API endpoints are documented
 * - Verifying documentation quality and consistency
 * 
 * Features:
 * - Automated freshness checking based on file modification times
 * - Cross-reference validation between code and documentation
 * - Quality metrics calculation and reporting
 * - Integration with CI/CD pipeline for automated validation
 * 
 * @author Learning Portal Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Endpoint(id = "documentation-validation")
public class DocumentationValidationService {
    
    private static final Logger log = LoggerFactory.getLogger(DocumentationValidationService.class);
    
    // Documentation validation thresholds
    private static final int MAX_DAYS_WITHOUT_UPDATE = 30;
    private static final double MIN_COMPLETENESS_PERCENTAGE = 80.0;
    private static final int MIN_WORDS_PER_SECTION = 50;
    
    // File patterns for different types of documentation
    private static final Pattern API_DOC_PATTERN = Pattern.compile(".*api.*\\.md$", Pattern.CASE_INSENSITIVE);
    private static final Pattern ARCHITECTURE_DOC_PATTERN = Pattern.compile(".*architecture.*\\.md$", Pattern.CASE_INSENSITIVE);
    private static final Pattern TROUBLESHOOTING_DOC_PATTERN = Pattern.compile(".*troubleshooting.*\\.md$", Pattern.CASE_INSENSITIVE);
    
    /**
     * Performs comprehensive validation of all documentation.
     * 
     * This method validates:
     * - Documentation freshness and currency
     * - Completeness against code changes
     * - Quality metrics and consistency
     * - Cross-references and links
     * 
     * @return ValidationReport containing detailed validation results
     */
    @ReadOperation
    public ValidationReport validateDocumentation() {
        log.info("Starting comprehensive documentation validation");
        
        try {
            ValidationReport report = new ValidationReport();
            report.setValidationTime(LocalDateTime.now());
            
            // Validate documentation freshness
            FreshnessValidation freshness = validateFreshness();
            report.setFreshnessValidation(freshness);
            
            // Validate documentation completeness
            CompletenessValidation completeness = validateCompleteness();
            report.setCompletenessValidation(completeness);
            
            // Validate documentation quality
            QualityValidation quality = validateQuality();
            report.setQualityValidation(quality);
            
            // Calculate overall score
            double overallScore = calculateOverallScore(freshness, completeness, quality);
            report.setOverallScore(overallScore);
            
            // Determine validation status
            ValidationStatus status = determineValidationStatus(overallScore, freshness, completeness, quality);
            report.setStatus(status);
            
            log.info("Documentation validation completed. Overall score: {:.1f}%, Status: {}", 
                    overallScore, status);
            
            return report;
            
        } catch (Exception e) {
            log.error("Failed to validate documentation", e);
            throw new DocumentationValidationException("Documentation validation failed", e);
        }
    }
    
    /**
     * Validates documentation freshness by checking file modification times.
     * 
     * @return FreshnessValidation results
     * @throws IOException if file system access fails
     */
    private FreshnessValidation validateFreshness() throws IOException {
        log.debug("Validating documentation freshness");
        
        FreshnessValidation validation = new FreshnessValidation();
        List<String> outdatedFiles = new ArrayList<>();
        List<String> recentlyUpdated = new ArrayList<>();
        
        Path docsPath = Paths.get("docs");
        if (!Files.exists(docsPath)) {
            validation.setStatus("FAILED");
            validation.setMessage("Documentation directory not found");
            return validation;
        }
        
        try (Stream<Path> paths = Files.walk(docsPath)) {
            List<Path> markdownFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".md"))
                    .collect(Collectors.toList());
            
            LocalDateTime threshold = LocalDateTime.now().minusDays(MAX_DAYS_WITHOUT_UPDATE);
            
            for (Path file : markdownFiles) {
                LocalDateTime lastModified = LocalDateTime.ofInstant(
                        Files.getLastModifiedTime(file).toInstant(),
                        java.time.ZoneId.systemDefault()
                );
                
                String relativePath = docsPath.relativize(file).toString();
                
                if (lastModified.isBefore(threshold)) {
                    outdatedFiles.add(relativePath + " (last updated: " + 
                            lastModified.format(DateTimeFormatter.ISO_LOCAL_DATE) + ")");
                } else {
                    recentlyUpdated.add(relativePath);
                }
            }
            
            validation.setTotalFiles(markdownFiles.size());
            validation.setOutdatedFiles(outdatedFiles);
            validation.setRecentlyUpdatedFiles(recentlyUpdated);
            
            double freshnessScore = markdownFiles.isEmpty() ? 0 : 
                    (double) recentlyUpdated.size() / markdownFiles.size() * 100;
            validation.setFreshnessScore(freshnessScore);
            
            if (outdatedFiles.isEmpty()) {
                validation.setStatus("PASSED");
                validation.setMessage("All documentation files are up to date");
            } else {
                validation.setStatus("WARNING");
                validation.setMessage(outdatedFiles.size() + " files may be outdated");
            }
        }
        
        log.debug("Freshness validation completed. Score: {:.1f}%", validation.getFreshnessScore());
        return validation;
    }
    
    /**
     * Validates documentation completeness against code changes.
     * 
     * @return CompletenessValidation results
     * @throws IOException if file system access fails
     */
    private CompletenessValidation validateCompleteness() throws IOException {
        log.debug("Validating documentation completeness");
        
        CompletenessValidation validation = new CompletenessValidation();
        List<String> missingDocumentation = new ArrayList<>();
        List<String> wellDocumented = new ArrayList<>();
        
        // Check for required documentation files
        String[] requiredFiles = {
                "docs/README.md",
                "docs/api/README.md",
                "docs/architecture/README.md",
                "docs/deployment/README.md",
                "docs/troubleshooting/README.md",
                "DEVELOPER_ONBOARDING.md"
        };
        
        for (String requiredFile : requiredFiles) {
            Path filePath = Paths.get(requiredFile);
            if (!Files.exists(filePath)) {
                missingDocumentation.add(requiredFile + " (required file missing)");
            } else if (Files.size(filePath) < 100) { // Less than 100 bytes
                missingDocumentation.add(requiredFile + " (file too small, likely empty)");
            } else {
                wellDocumented.add(requiredFile);
            }
        }
        
        // Check API endpoint documentation coverage
        int totalEndpoints = countRestEndpoints();
        int documentedEndpoints = countDocumentedEndpoints();
        
        validation.setTotalEndpoints(totalEndpoints);
        validation.setDocumentedEndpoints(documentedEndpoints);
        
        double endpointCoverage = totalEndpoints > 0 ? 
                (double) documentedEndpoints / totalEndpoints * 100 : 100;
        validation.setEndpointCoverage(endpointCoverage);
        
        // Check for undocumented controllers
        List<String> undocumentedControllers = findUndocumentedControllers();
        validation.setUndocumentedControllers(undocumentedControllers);
        
        validation.setMissingDocumentation(missingDocumentation);
        validation.setWellDocumentedAreas(wellDocumented);
        
        double completenessScore = calculateCompletenessScore(
                requiredFiles.length, missingDocumentation.size(), endpointCoverage);
        validation.setCompletenessScore(completenessScore);
        
        if (completenessScore >= MIN_COMPLETENESS_PERCENTAGE) {
            validation.setStatus("PASSED");
            validation.setMessage("Documentation completeness meets requirements");
        } else {
            validation.setStatus("FAILED");
            validation.setMessage("Documentation completeness below threshold: " + 
                    String.format("%.1f%% < %.1f%%", completenessScore, MIN_COMPLETENESS_PERCENTAGE));
        }
        
        log.debug("Completeness validation completed. Score: {:.1f}%", completenessScore);
        return validation;
    }
    
    /**
     * Validates documentation quality and consistency.
     * 
     * @return QualityValidation results
     * @throws IOException if file system access fails
     */
    private QualityValidation validateQuality() throws IOException {
        log.debug("Validating documentation quality");
        
        QualityValidation validation = new QualityValidation();
        List<String> qualityIssues = new ArrayList<>();
        List<String> qualityHighlights = new ArrayList<>();
        
        Path docsPath = Paths.get("docs");
        if (!Files.exists(docsPath)) {
            validation.setStatus("FAILED");
            validation.setMessage("Documentation directory not found");
            return validation;
        }
        
        try (Stream<Path> paths = Files.walk(docsPath)) {
            List<Path> markdownFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".md"))
                    .collect(Collectors.toList());
            
            int totalFiles = markdownFiles.size();
            int highQualityFiles = 0;
            
            for (Path file : markdownFiles) {
                String content = Files.readString(file);
                String relativePath = docsPath.relativize(file).toString();
                
                // Check file length
                if (content.length() < 500) {
                    qualityIssues.add(relativePath + " - Content too short (< 500 characters)");
                }
                
                // Check for proper headings
                if (!content.contains("# ") && !content.contains("## ")) {
                    qualityIssues.add(relativePath + " - Missing proper headings");
                }
                
                // Check for code examples in technical docs
                if (isApiOrArchitectureDoc(relativePath) && !content.contains("```")) {
                    qualityIssues.add(relativePath + " - Technical doc missing code examples");
                }
                
                // Check for table of contents in long documents
                if (content.length() > 5000 && !content.toLowerCase().contains("table of contents")) {
                    qualityIssues.add(relativePath + " - Long document missing table of contents");
                }
                
                // Check for broken internal links
                List<String> brokenLinks = findBrokenInternalLinks(content, file.getParent());
                for (String brokenLink : brokenLinks) {
                    qualityIssues.add(relativePath + " - Broken internal link: " + brokenLink);
                }
                
                // Positive quality indicators
                if (content.length() > 2000 && content.contains("```") && 
                    content.contains("## ") && content.contains("### ")) {
                    highQualityFiles++;
                    qualityHighlights.add(relativePath + " - Well-structured with examples");
                }
            }
            
            validation.setTotalFiles(totalFiles);
            validation.setHighQualityFiles(highQualityFiles);
            validation.setQualityIssues(qualityIssues);
            validation.setQualityHighlights(qualityHighlights);
            
            double qualityScore = totalFiles > 0 ? 
                    (double) highQualityFiles / totalFiles * 100 : 0;
            validation.setQualityScore(qualityScore);
            
            if (qualityIssues.size() <= totalFiles * 0.1) { // Less than 10% of files have issues
                validation.setStatus("PASSED");
                validation.setMessage("Documentation quality meets standards");
            } else {
                validation.setStatus("WARNING");
                validation.setMessage(qualityIssues.size() + " quality issues found");
            }
        }
        
        log.debug("Quality validation completed. Score: {:.1f}%", validation.getQualityScore());
        return validation;
    }
    
    /**
     * Counts the total number of REST endpoints in the application.
     * 
     * @return Number of REST endpoints
     * @throws IOException if source code scanning fails
     */
    private int countRestEndpoints() throws IOException {
        Path srcPath = Paths.get("src/main/java");
        if (!Files.exists(srcPath)) {
            return 0;
        }
        
        try (Stream<Path> paths = Files.walk(srcPath)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .mapToInt(this::countEndpointsInFile)
                    .sum();
        }
    }
    
    /**
     * Counts REST endpoints in a single Java file.
     * 
     * @param javaFile The Java file to scan
     * @return Number of endpoints in the file
     */
    private int countEndpointsInFile(Path javaFile) {
        try {
            String content = Files.readString(javaFile);
            
            // Count mapping annotations
            Pattern mappingPattern = Pattern.compile(
                    "@(GetMapping|PostMapping|PutMapping|DeleteMapping|PatchMapping|RequestMapping)");
            Matcher matcher = mappingPattern.matcher(content);
            
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            
            return count;
        } catch (IOException e) {
            log.warn("Failed to read Java file: {}", javaFile, e);
            return 0;
        }
    }
    
    /**
     * Counts documented endpoints by scanning API documentation.
     * 
     * @return Number of documented endpoints
     * @throws IOException if documentation scanning fails
     */
    private int countDocumentedEndpoints() throws IOException {
        Path apiDocsPath = Paths.get("docs/api");
        if (!Files.exists(apiDocsPath)) {
            return 0;
        }
        
        try (Stream<Path> paths = Files.walk(apiDocsPath)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".md"))
                    .mapToInt(this::countDocumentedEndpointsInFile)
                    .sum();
        }
    }
    
    /**
     * Counts documented endpoints in a single documentation file.
     * 
     * @param docFile The documentation file to scan
     * @return Number of documented endpoints
     */
    private int countDocumentedEndpointsInFile(Path docFile) {
        try {
            String content = Files.readString(docFile);
            
            // Count HTTP method headers (GET, POST, etc.)
            Pattern endpointPattern = Pattern.compile("###?\\s+(GET|POST|PUT|DELETE|PATCH)\\s+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = endpointPattern.matcher(content);
            
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            
            return count;
        } catch (IOException e) {
            log.warn("Failed to read documentation file: {}", docFile, e);
            return 0;
        }
    }
    
    /**
     * Finds controllers that lack proper documentation.
     * 
     * @return List of undocumented controller names
     * @throws IOException if source code scanning fails
     */
    private List<String> findUndocumentedControllers() throws IOException {
        List<String> undocumented = new ArrayList<>();
        
        Path srcPath = Paths.get("src/main/java");
        if (!Files.exists(srcPath)) {
            return undocumented;
        }
        
        try (Stream<Path> paths = Files.walk(srcPath)) {
            List<Path> controllerFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith("Controller.java"))
                    .collect(Collectors.toList());
            
            for (Path controllerFile : controllerFiles) {
                String content = Files.readString(controllerFile);
                String className = controllerFile.getFileName().toString().replace(".java", "");
                
                // Check if controller has proper class-level documentation
                if (!content.contains("/**") || !content.contains("@author")) {
                    undocumented.add(className + " - Missing class-level JavaDoc");
                }
                
                // Check if controller methods have documentation
                Pattern methodPattern = Pattern.compile("@(GetMapping|PostMapping|PutMapping|DeleteMapping|PatchMapping)");
                Matcher matcher = methodPattern.matcher(content);
                
                while (matcher.find()) {
                    int start = matcher.start();
                    String beforeMapping = content.substring(Math.max(0, start - 200), start);
                    
                    if (!beforeMapping.contains("/**")) {
                        undocumented.add(className + " - Method missing JavaDoc at line ~" + 
                                content.substring(0, start).split("\n").length);
                    }
                }
            }
        }
        
        return undocumented;
    }
    
    /**
     * Checks if a documentation file is API or architecture related.
     * 
     * @param relativePath The relative path of the file
     * @return true if the file is technical documentation
     */
    private boolean isApiOrArchitectureDoc(String relativePath) {
        return API_DOC_PATTERN.matcher(relativePath).matches() ||
               ARCHITECTURE_DOC_PATTERN.matcher(relativePath).matches();
    }
    
    /**
     * Finds broken internal links in documentation content.
     * 
     * @param content The documentation content
     * @param basePath The base path for resolving relative links
     * @return List of broken internal links
     */
    private List<String> findBrokenInternalLinks(String content, Path basePath) {
        List<String> brokenLinks = new ArrayList<>();
        
        // Pattern to match markdown links [text](path)
        Pattern linkPattern = Pattern.compile("\\[([^\\]]+)\\]\\(([^\\)]+)\\)");
        Matcher matcher = linkPattern.matcher(content);
        
        while (matcher.find()) {
            String linkPath = matcher.group(2);
            
            // Skip external links
            if (linkPath.startsWith("http://") || linkPath.startsWith("https://")) {
                continue;
            }
            
            // Skip anchors
            if (linkPath.startsWith("#")) {
                continue;
            }
            
            // Check if internal link exists
            Path targetPath = basePath.resolve(linkPath);
            if (!Files.exists(targetPath)) {
                brokenLinks.add(linkPath);
            }
        }
        
        return brokenLinks;
    }
    
    /**
     * Calculates completeness score based on various factors.
     * 
     * @param totalRequired Total number of required files
     * @param missing Number of missing files
     * @param endpointCoverage Percentage of documented endpoints
     * @return Completeness score as percentage
     */
    private double calculateCompletenessScore(int totalRequired, int missing, double endpointCoverage) {
        double fileScore = totalRequired > 0 ? 
                (double) (totalRequired - missing) / totalRequired * 100 : 100;
        
        // Weight: 60% file completeness, 40% endpoint coverage
        return (fileScore * 0.6) + (endpointCoverage * 0.4);
    }
    
    /**
     * Calculates overall validation score.
     * 
     * @param freshness Freshness validation results
     * @param completeness Completeness validation results
     * @param quality Quality validation results
     * @return Overall score as percentage
     */
    private double calculateOverallScore(FreshnessValidation freshness, 
                                       CompletenessValidation completeness, 
                                       QualityValidation quality) {
        // Weight: 30% freshness, 40% completeness, 30% quality
        return (freshness.getFreshnessScore() * 0.3) + 
               (completeness.getCompletenessScore() * 0.4) + 
               (quality.getQualityScore() * 0.3);
    }
    
    /**
     * Determines overall validation status based on individual validation results.
     * 
     * @param overallScore Overall validation score
     * @param freshness Freshness validation results
     * @param completeness Completeness validation results
     * @param quality Quality validation results
     * @return Overall validation status
     */
    private ValidationStatus determineValidationStatus(double overallScore,
                                                     FreshnessValidation freshness,
                                                     CompletenessValidation completeness,
                                                     QualityValidation quality) {
        if ("FAILED".equals(completeness.getStatus())) {
            return ValidationStatus.FAILED;
        }
        
        if (overallScore >= 90) {
            return ValidationStatus.EXCELLENT;
        } else if (overallScore >= 80) {
            return ValidationStatus.GOOD;
        } else if (overallScore >= 70) {
            return ValidationStatus.ACCEPTABLE;
        } else {
            return ValidationStatus.NEEDS_IMPROVEMENT;
        }
    }
    
    // Data classes for validation results
    
    public static class ValidationReport {
        private LocalDateTime validationTime;
        private FreshnessValidation freshnessValidation;
        private CompletenessValidation completenessValidation;
        private QualityValidation qualityValidation;
        private double overallScore;
        private ValidationStatus status;
        
        // Getters and setters
        public LocalDateTime getValidationTime() { return validationTime; }
        public void setValidationTime(LocalDateTime validationTime) { this.validationTime = validationTime; }
        public FreshnessValidation getFreshnessValidation() { return freshnessValidation; }
        public void setFreshnessValidation(FreshnessValidation freshnessValidation) { this.freshnessValidation = freshnessValidation; }
        public CompletenessValidation getCompletenessValidation() { return completenessValidation; }
        public void setCompletenessValidation(CompletenessValidation completenessValidation) { this.completenessValidation = completenessValidation; }
        public QualityValidation getQualityValidation() { return qualityValidation; }
        public void setQualityValidation(QualityValidation qualityValidation) { this.qualityValidation = qualityValidation; }
        public double getOverallScore() { return overallScore; }
        public void setOverallScore(double overallScore) { this.overallScore = overallScore; }
        public ValidationStatus getStatus() { return status; }
        public void setStatus(ValidationStatus status) { this.status = status; }
    }
    
    public static class FreshnessValidation {
        private String status;
        private String message;
        private int totalFiles;
        private List<String> outdatedFiles;
        private List<String> recentlyUpdatedFiles;
        private double freshnessScore;
        
        // Getters and setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public int getTotalFiles() { return totalFiles; }
        public void setTotalFiles(int totalFiles) { this.totalFiles = totalFiles; }
        public List<String> getOutdatedFiles() { return outdatedFiles; }
        public void setOutdatedFiles(List<String> outdatedFiles) { this.outdatedFiles = outdatedFiles; }
        public List<String> getRecentlyUpdatedFiles() { return recentlyUpdatedFiles; }
        public void setRecentlyUpdatedFiles(List<String> recentlyUpdatedFiles) { this.recentlyUpdatedFiles = recentlyUpdatedFiles; }
        public double getFreshnessScore() { return freshnessScore; }
        public void setFreshnessScore(double freshnessScore) { this.freshnessScore = freshnessScore; }
    }
    
    public static class CompletenessValidation {
        private String status;
        private String message;
        private int totalEndpoints;
        private int documentedEndpoints;
        private double endpointCoverage;
        private List<String> undocumentedControllers;
        private List<String> missingDocumentation;
        private List<String> wellDocumentedAreas;
        private double completenessScore;
        
        // Getters and setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public int getTotalEndpoints() { return totalEndpoints; }
        public void setTotalEndpoints(int totalEndpoints) { this.totalEndpoints = totalEndpoints; }
        public int getDocumentedEndpoints() { return documentedEndpoints; }
        public void setDocumentedEndpoints(int documentedEndpoints) { this.documentedEndpoints = documentedEndpoints; }
        public double getEndpointCoverage() { return endpointCoverage; }
        public void setEndpointCoverage(double endpointCoverage) { this.endpointCoverage = endpointCoverage; }
        public List<String> getUndocumentedControllers() { return undocumentedControllers; }
        public void setUndocumentedControllers(List<String> undocumentedControllers) { this.undocumentedControllers = undocumentedControllers; }
        public List<String> getMissingDocumentation() { return missingDocumentation; }
        public void setMissingDocumentation(List<String> missingDocumentation) { this.missingDocumentation = missingDocumentation; }
        public List<String> getWellDocumentedAreas() { return wellDocumentedAreas; }
        public void setWellDocumentedAreas(List<String> wellDocumentedAreas) { this.wellDocumentedAreas = wellDocumentedAreas; }
        public double getCompletenessScore() { return completenessScore; }
        public void setCompletenessScore(double completenessScore) { this.completenessScore = completenessScore; }
    }
    
    public static class QualityValidation {
        private String status;
        private String message;
        private int totalFiles;
        private int highQualityFiles;
        private List<String> qualityIssues;
        private List<String> qualityHighlights;
        private double qualityScore;
        
        // Getters and setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public int getTotalFiles() { return totalFiles; }
        public void setTotalFiles(int totalFiles) { this.totalFiles = totalFiles; }
        public int getHighQualityFiles() { return highQualityFiles; }
        public void setHighQualityFiles(int highQualityFiles) { this.highQualityFiles = highQualityFiles; }
        public List<String> getQualityIssues() { return qualityIssues; }
        public void setQualityIssues(List<String> qualityIssues) { this.qualityIssues = qualityIssues; }
        public List<String> getQualityHighlights() { return qualityHighlights; }
        public void setQualityHighlights(List<String> qualityHighlights) { this.qualityHighlights = qualityHighlights; }
        public double getQualityScore() { return qualityScore; }
        public void setQualityScore(double qualityScore) { this.qualityScore = qualityScore; }
    }
    
    public enum ValidationStatus {
        EXCELLENT, GOOD, ACCEPTABLE, NEEDS_IMPROVEMENT, FAILED
    }
    
    public static class DocumentationValidationException extends RuntimeException {
        public DocumentationValidationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}