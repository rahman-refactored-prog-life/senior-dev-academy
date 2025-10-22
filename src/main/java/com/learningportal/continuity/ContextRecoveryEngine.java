package com.learningportal.continuity;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Context Recovery Engine that intelligently reconstructs missing context
 * from multiple sources including Git history, documentation files, and project artifacts.
 */
@Service
public class ContextRecoveryEngine {
    
    private static final Logger logger = LoggerFactory.getLogger(ContextRecoveryEngine.class);
    
    // Documentation files to analyze for context recovery
    private static final String[] CONTEXT_SOURCES = {
        "CURRENT_STATUS.md",
        "PROJECT_CONVERSATION_LOG.md", 
        "PROJECT_SCOPE_AND_TRACKING.md",
        "DEVELOPMENT_GUIDE.md",
        "README.md",
        "session-continuity-brief.md",
        "temporary.md"
    };
    
    /**
     * Analyzes all available sources for context recovery.
     * 
     * @return List of source analyses with reliability scores
     */
    public List<SourceAnalysis> analyzeAvailableSources() {
        logger.info("Analyzing available sources for context recovery");
        
        List<SourceAnalysis> analyses = new ArrayList<>();
        
        // Analyze Git history
        analyses.add(analyzeGitHistory());
        
        // Analyze documentation files
        for (String fileName : CONTEXT_SOURCES) {
            analyses.add(analyzeDocumentationFile(fileName));
        }
        
        // Analyze project structure
        analyses.add(analyzeProjectStructure());
        
        // Analyze configuration files
        analyses.add(analyzeConfigurationFiles());
        
        logger.info("Source analysis completed: {} sources analyzed", analyses.size());
        return analyses;
    }
    
    /**
     * Reconstructs context from available sources with confidence scoring.
     * 
     * @return Reconstructed context with confidence metrics
     */
    public ReconstructedContext reconstructContext() {
        logger.info("Starting intelligent context reconstruction");
        
        ReconstructedContext context = new ReconstructedContext();
        context.setReconstructionTime(LocalDateTime.now());
        
        // Analyze available sources
        List<SourceAnalysis> sourceAnalyses = analyzeAvailableSources();
        
        // Extract context elements from sources
        List<ContextElement> elements = extractContextElements(sourceAnalyses);
        
        // Correlate and validate context elements
        List<ContextElement> validatedElements = correlateContextElements(elements);
        
        // Calculate overall confidence
        double confidence = calculateOverallConfidence(validatedElements, sourceAnalyses);
        
        // Identify missing elements
        List<String> missingElements = identifyMissingElements(validatedElements);
        
        // Generate recovery recommendations
        List<String> recommendations = generateRecoveryRecommendations(missingElements, sourceAnalyses);
        
        context.setConfidence(confidence);
        context.setReconstructedElements(validatedElements);
        context.setMissingElements(missingElements);
        context.setRecommendedActions(recommendations);
        
        logger.info("Context reconstruction completed: {}% confidence, {} elements recovered, {} missing", 
                   String.format("%.1f", confidence), validatedElements.size(), missingElements.size());
        
        return context;
    }
    
    /**
     * Validates reconstructed context against known project requirements.
     * 
     * @param reconstructedContext The context to validate
     * @return Validation result with accuracy assessment
     */
    public ValidationResult validateReconstruction(ReconstructedContext reconstructedContext) {
        logger.info("Validating reconstructed context accuracy");
        
        ValidationResult result = new ValidationResult();
        result.setValidationTime(LocalDateTime.now());
        
        List<String> issues = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        double score = 100.0;
        
        // Validate confidence threshold
        if (reconstructedContext.getConfidence() < 70.0) {
            issues.add("Low reconstruction confidence: " + String.format("%.1f", reconstructedContext.getConfidence()) + "%");
            score -= 20.0;
            recommendations.add("Gather additional context sources to improve confidence");
        }
        
        // Validate essential elements presence
        List<String> essentialElements = Arrays.asList(
            "session_id", "current_phase", "progress_percentage", 
            "compilation_status", "next_actions"
        );
        
        for (String essential : essentialElements) {
            boolean found = reconstructedContext.getReconstructedElements().stream()
                .anyMatch(element -> element.getElementType().toLowerCase().contains(essential.replace("_", "")));
            
            if (!found) {
                issues.add("Missing essential element: " + essential);
                score -= 15.0;
            }
        }
        
        // Validate element consistency
        Map<String, List<ContextElement>> elementGroups = groupElementsByType(reconstructedContext.getReconstructedElements());
        for (Map.Entry<String, List<ContextElement>> entry : elementGroups.entrySet()) {
            if (entry.getValue().size() > 1) {
                // Check for conflicting values
                Set<String> uniqueValues = new HashSet<>();
                for (ContextElement element : entry.getValue()) {
                    uniqueValues.add(element.getValue());
                }
                
                if (uniqueValues.size() > 1) {
                    issues.add("Conflicting values for " + entry.getKey() + ": " + uniqueValues);
                    score -= 10.0;
                    recommendations.add("Resolve conflicts in " + entry.getKey() + " values");
                }
            }
        }
        
        result.setValid(score >= 60.0); // Require 60% score for valid reconstruction
        result.setScore(Math.max(0, score));
        result.setIssues(issues);
        result.setRecommendations(recommendations);
        
        logger.info("Context validation completed: Valid={}, Score={}", result.isValid(), String.format("%.1f", score));
        return result;
    }
    
    /**
     * Reconstructs context for a specific session ID (interface compatibility method).
     * 
     * @param sessionId The session ID to reconstruct context for
     * @return Reconstructed context with confidence metrics
     */
    public ReconstructedContext reconstructContext(String sessionId) {
        logger.info("Reconstructing context for session ID: {}", sessionId);
        
        // Call the main reconstruction method
        ReconstructedContext context = reconstructContext();
        
        // Set the session ID in the context
        context.setSessionId(sessionId);
        
        return context;
    }
    
    /**
     * Generates comprehensive recovery report with findings and recommendations.
     * 
     * @param reconstructedContext The reconstructed context
     * @param validationResult The validation results
     * @return Detailed recovery report
     */
    public RecoveryReport generateRecoveryReport(ReconstructedContext reconstructedContext, ValidationResult validationResult) {
        logger.info("Generating comprehensive recovery report");
        
        RecoveryReport report = new RecoveryReport();
        report.setReportTime(LocalDateTime.now());
        report.setOverallSuccess(validationResult.isValid());
        report.setConfidenceScore(reconstructedContext.getConfidence());
        report.setValidationScore(validationResult.getScore());
        
        // Recovery statistics
        report.setElementsRecovered(reconstructedContext.getReconstructedElements().size());
        report.setElementsMissing(reconstructedContext.getMissingElements().size());
        report.setSourcesAnalyzed(CONTEXT_SOURCES.length + 3); // Files + Git + Project + Config
        
        // Generate summary
        StringBuilder summary = new StringBuilder();
        summary.append("Context Recovery Summary:\n");
        summary.append("- Confidence: ").append(String.format("%.1f", reconstructedContext.getConfidence())).append("%\n");
        summary.append("- Elements Recovered: ").append(reconstructedContext.getReconstructedElements().size()).append("\n");
        summary.append("- Elements Missing: ").append(reconstructedContext.getMissingElements().size()).append("\n");
        summary.append("- Validation Score: ").append(String.format("%.1f", validationResult.getScore())).append("%\n");
        summary.append("- Overall Status: ").append(validationResult.isValid() ? "SUCCESS" : "PARTIAL");
        
        report.setSummary(summary.toString());
        
        // Combine recommendations
        List<String> allRecommendations = new ArrayList<>();
        if (reconstructedContext.getRecommendedActions() != null) {
            allRecommendations.addAll(reconstructedContext.getRecommendedActions());
        }
        if (validationResult.getRecommendations() != null) {
            allRecommendations.addAll(validationResult.getRecommendations());
        }
        report.setRecommendations(allRecommendations);
        
        // Recovery actions taken
        report.setRecoveryActions(Arrays.asList(
            "Analyzed Git commit history for recent changes",
            "Extracted context from " + CONTEXT_SOURCES.length + " documentation files",
            "Validated project structure and configuration",
            "Cross-referenced information across multiple sources",
            "Generated confidence scores for all recovered elements"
        ));
        
        logger.info("Recovery report generated: {} elements recovered with {}% confidence", 
                   report.getElementsRecovered(), String.format("%.1f", report.getConfidenceScore()));
        
        return report;
    }
    
    // Private helper methods for source analysis
    
    private SourceAnalysis analyzeGitHistory() {
        logger.debug("Analyzing Git commit history");
        
        SourceAnalysis analysis = new SourceAnalysis();
        analysis.setSourceName("Git History");
        analysis.setSourceType("Version Control");
        analysis.setAnalysisTime(LocalDateTime.now());
        
        try {
            // Get recent commits
            ProcessBuilder pb = new ProcessBuilder("git", "log", "--oneline", "-10");
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            List<String> commits = new ArrayList<>();
            String line;
            
            while ((line = reader.readLine()) != null) {
                commits.add(line);
            }
            
            process.waitFor();
            
            analysis.setAvailable(true);
            analysis.setReliabilityScore(85.0); // Git history is highly reliable
            analysis.setDataQuality("HIGH");
            analysis.setExtractedData(Map.of(
                "recent_commits", String.join("\n", commits),
                "commit_count", String.valueOf(commits.size())
            ));
            
        } catch (Exception e) {
            logger.warn("Error analyzing Git history: {}", e.getMessage());
            analysis.setAvailable(false);
            analysis.setReliabilityScore(0.0);
            analysis.setDataQuality("UNAVAILABLE");
        }
        
        return analysis;
    }
    
    private SourceAnalysis analyzeDocumentationFile(String fileName) {
        logger.debug("Analyzing documentation file: {}", fileName);
        
        SourceAnalysis analysis = new SourceAnalysis();
        analysis.setSourceName(fileName);
        analysis.setSourceType("Documentation");
        analysis.setAnalysisTime(LocalDateTime.now());
        
        try {
            Path filePath = Paths.get(fileName);
            
            if (Files.exists(filePath)) {
                String content = Files.readString(filePath);
                
                // Extract key information using patterns
                Map<String, String> extractedData = extractDataFromContent(content);
                
                analysis.setAvailable(true);
                analysis.setReliabilityScore(calculateFileReliability(fileName, content));
                analysis.setDataQuality(assessDataQuality(content));
                analysis.setExtractedData(extractedData);
                
            } else {
                analysis.setAvailable(false);
                analysis.setReliabilityScore(0.0);
                analysis.setDataQuality("MISSING");
            }
            
        } catch (Exception e) {
            logger.warn("Error analyzing file {}: {}", fileName, e.getMessage());
            analysis.setAvailable(false);
            analysis.setReliabilityScore(0.0);
            analysis.setDataQuality("ERROR");
        }
        
        return analysis;
    }
    
    private SourceAnalysis analyzeProjectStructure() {
        logger.debug("Analyzing project structure");
        
        SourceAnalysis analysis = new SourceAnalysis();
        analysis.setSourceName("Project Structure");
        analysis.setSourceType("File System");
        analysis.setAnalysisTime(LocalDateTime.now());
        
        try {
            Map<String, String> structureData = new HashMap<>();
            
            // Check key directories
            structureData.put("has_src_main_java", String.valueOf(Files.exists(Paths.get("src/main/java"))));
            structureData.put("has_frontend", String.valueOf(Files.exists(Paths.get("frontend"))));
            structureData.put("has_pom_xml", String.valueOf(Files.exists(Paths.get("pom.xml"))));
            structureData.put("has_package_json", String.valueOf(Files.exists(Paths.get("frontend/package.json"))));
            
            analysis.setAvailable(true);
            analysis.setReliabilityScore(70.0); // Project structure is moderately reliable
            analysis.setDataQuality("MEDIUM");
            analysis.setExtractedData(structureData);
            
        } catch (Exception e) {
            logger.warn("Error analyzing project structure: {}", e.getMessage());
            analysis.setAvailable(false);
            analysis.setReliabilityScore(0.0);
            analysis.setDataQuality("ERROR");
        }
        
        return analysis;
    }
    
    private SourceAnalysis analyzeConfigurationFiles() {
        logger.debug("Analyzing configuration files");
        
        SourceAnalysis analysis = new SourceAnalysis();
        analysis.setSourceName("Configuration Files");
        analysis.setSourceType("Configuration");
        analysis.setAnalysisTime(LocalDateTime.now());
        
        try {
            Map<String, String> configData = new HashMap<>();
            
            // Analyze application.yml
            if (Files.exists(Paths.get("src/main/resources/application.yml"))) {
                String appConfig = Files.readString(Paths.get("src/main/resources/application.yml"));
                configData.put("database_type", extractDatabaseType(appConfig));
                configData.put("server_port", extractServerPort(appConfig));
            }
            
            analysis.setAvailable(true);
            analysis.setReliabilityScore(60.0); // Configuration is somewhat reliable
            analysis.setDataQuality("MEDIUM");
            analysis.setExtractedData(configData);
            
        } catch (Exception e) {
            logger.warn("Error analyzing configuration files: {}", e.getMessage());
            analysis.setAvailable(false);
            analysis.setReliabilityScore(0.0);
            analysis.setDataQuality("ERROR");
        }
        
        return analysis;
    }
    
    private Map<String, String> extractDataFromContent(String content) {
        Map<String, String> data = new HashMap<>();
        
        // Extract session ID
        Pattern sessionPattern = Pattern.compile("Session[:\\s]+([\\w_]+)");
        Matcher sessionMatcher = sessionPattern.matcher(content);
        if (sessionMatcher.find()) {
            data.put("session_id", sessionMatcher.group(1));
        }
        
        // Extract progress percentage
        Pattern progressPattern = Pattern.compile("Progress[:\\s]+(\\d+(?:\\.\\d+)?)%");
        Matcher progressMatcher = progressPattern.matcher(content);
        if (progressMatcher.find()) {
            data.put("progress_percentage", progressMatcher.group(1));
        }
        
        // Extract current phase
        Pattern phasePattern = Pattern.compile("Phase[:\\s]+([\\w-]+)");
        Matcher phaseMatcher = phasePattern.matcher(content);
        if (phaseMatcher.find()) {
            data.put("current_phase", phaseMatcher.group(1));
        }
        
        // Extract compilation status
        if (content.contains("SUCCESS") || content.contains("PASSED")) {
            data.put("compilation_status", "SUCCESS");
        } else if (content.contains("FAILED") || content.contains("ERROR")) {
            data.put("compilation_status", "FAILED");
        }
        
        return data;
    }
    
    private double calculateFileReliability(String fileName, String content) {
        double reliability = 50.0; // Base reliability
        
        // Higher reliability for certain files
        if (fileName.equals("CURRENT_STATUS.md")) {
            reliability = 90.0;
        } else if (fileName.equals("session-continuity-brief.md")) {
            reliability = 85.0;
        } else if (fileName.equals("PROJECT_CONVERSATION_LOG.md")) {
            reliability = 80.0;
        }
        
        // Adjust based on content freshness (recent timestamps)
        if (content.contains(LocalDateTime.now().toLocalDate().toString())) {
            reliability += 10.0;
        }
        
        // Adjust based on content completeness
        if (content.length() > 1000) {
            reliability += 5.0;
        }
        
        return Math.min(100.0, reliability);
    }
    
    private String assessDataQuality(String content) {
        if (content.length() < 100) {
            return "LOW";
        } else if (content.length() < 500) {
            return "MEDIUM";
        } else {
            return "HIGH";
        }
    }
    
    private String extractDatabaseType(String config) {
        if (config.contains("h2")) return "H2";
        if (config.contains("postgresql")) return "PostgreSQL";
        if (config.contains("mysql")) return "MySQL";
        return "Unknown";
    }
    
    private String extractServerPort(String config) {
        Pattern portPattern = Pattern.compile("port[:\\s]+(\\d+)");
        Matcher matcher = portPattern.matcher(config);
        return matcher.find() ? matcher.group(1) : "8080";
    }
    
    private List<ContextElement> extractContextElements(List<SourceAnalysis> sourceAnalyses) {
        List<ContextElement> elements = new ArrayList<>();
        
        for (SourceAnalysis analysis : sourceAnalyses) {
            if (analysis.isAvailable() && analysis.getExtractedData() != null) {
                for (Map.Entry<String, String> entry : analysis.getExtractedData().entrySet()) {
                    ContextElement element = new ContextElement();
                    element.setElementType(entry.getKey());
                    element.setValue(entry.getValue());
                    element.setSourceName(analysis.getSourceName());
                    element.setConfidence(analysis.getReliabilityScore());
                    element.setExtractionTime(LocalDateTime.now());
                    elements.add(element);
                }
            }
        }
        
        return elements;
    }
    
    private List<ContextElement> correlateContextElements(List<ContextElement> elements) {
        // Group elements by type and select the most reliable one
        Map<String, List<ContextElement>> grouped = new HashMap<>();
        
        for (ContextElement element : elements) {
            grouped.computeIfAbsent(element.getElementType(), k -> new ArrayList<>()).add(element);
        }
        
        List<ContextElement> correlated = new ArrayList<>();
        
        for (Map.Entry<String, List<ContextElement>> entry : grouped.entrySet()) {
            // Select element with highest confidence
            ContextElement best = entry.getValue().stream()
                .max(Comparator.comparing(ContextElement::getConfidence))
                .orElse(null);
            
            if (best != null) {
                correlated.add(best);
            }
        }
        
        return correlated;
    }
    
    private double calculateOverallConfidence(List<ContextElement> elements, List<SourceAnalysis> sources) {
        if (elements.isEmpty()) return 0.0;
        
        double totalConfidence = elements.stream().mapToDouble(ContextElement::getConfidence).sum();
        double averageConfidence = totalConfidence / elements.size();
        
        // Adjust based on number of available sources
        long availableSources = sources.stream().mapToLong(s -> s.isAvailable() ? 1 : 0).sum();
        double sourceBonus = (availableSources / (double) sources.size()) * 10.0;
        
        return Math.min(100.0, averageConfidence + sourceBonus);
    }
    
    private List<String> identifyMissingElements(List<ContextElement> elements) {
        List<String> essential = Arrays.asList(
            "session_id", "current_phase", "progress_percentage", 
            "compilation_status", "next_actions", "database_type"
        );
        
        Set<String> present = new HashSet<>();
        for (ContextElement element : elements) {
            present.add(element.getElementType());
        }
        
        return essential.stream()
            .filter(e -> !present.contains(e))
            .toList();
    }
    
    private List<String> generateRecoveryRecommendations(List<String> missingElements, List<SourceAnalysis> sources) {
        List<String> recommendations = new ArrayList<>();
        
        if (!missingElements.isEmpty()) {
            recommendations.add("Gather missing context elements: " + String.join(", ", missingElements));
        }
        
        long unavailableSources = sources.stream().mapToLong(s -> s.isAvailable() ? 0 : 1).sum();
        if (unavailableSources > 0) {
            recommendations.add("Restore " + unavailableSources + " unavailable context sources");
        }
        
        recommendations.add("Validate recovered context against current project state");
        recommendations.add("Update documentation to prevent future context loss");
        
        return recommendations;
    }
    
    private Map<String, List<ContextElement>> groupElementsByType(List<ContextElement> elements) {
        Map<String, List<ContextElement>> groups = new HashMap<>();
        
        for (ContextElement element : elements) {
            groups.computeIfAbsent(element.getElementType(), k -> new ArrayList<>()).add(element);
        }
        
        return groups;
    }
}