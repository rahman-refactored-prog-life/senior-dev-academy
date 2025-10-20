package com.learningportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Behavioral Interview Controller
 * 
 * Provides endpoints for behavioral interview preparation including Amazon Leadership Principles.
 */
@RestController
@RequestMapping("/behavioral")
@Tag(name = "Behavioral Interviews", description = "Behavioral interview preparation and Amazon Leadership Principles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BehavioralInterviewController {
    
    @Operation(
        summary = "Get Amazon Leadership Principles",
        description = "Returns all 16 Amazon Leadership Principles with explanations and examples"
    )
    @GetMapping("/amazon-leadership-principles")
    public ResponseEntity<Map<String, Object>> getAmazonLeadershipPrinciples() {
        Map<String, Object> principles = new HashMap<>();
        
        principles.put("title", "Amazon's 16 Leadership Principles");
        principles.put("description", "Core principles that guide decision-making at Amazon");
        principles.put("totalPrinciples", 16);
        
        List<Map<String, Object>> principlesList = Arrays.asList(
            createPrincipleMap("Customer Obsession", 
                             "Leaders start with the customer and work backwards. They work vigorously to earn and keep customer trust.",
                             "Tell me about a time you obsessed over a customer",
                             Arrays.asList("Focus on customer needs", "Long-term customer relationships", "Customer feedback integration")),
            
            createPrincipleMap("Ownership",
                             "Leaders are owners. They think long term and don't sacrifice long-term value for short-term results.",
                             "Describe a time you took ownership of a problem outside your job description",
                             Arrays.asList("Think long-term", "Take responsibility", "Act on behalf of entire company")),
            
            createPrincipleMap("Invent and Simplify",
                             "Leaders expect and require innovation and invention from their teams and always find ways to simplify.",
                             "Tell me about a time you invented or simplified a process",
                             Arrays.asList("Innovation mindset", "Simplification", "Process improvement")),
            
            createPrincipleMap("Are Right, A Lot",
                             "Leaders are right a lot. They have strong judgment and good instincts. They seek diverse perspectives.",
                             "Tell me about a time you were wrong and how you handled it",
                             Arrays.asList("Good judgment", "Learn from mistakes", "Seek diverse perspectives")),
            
            createPrincipleMap("Learn and Be Curious",
                             "Leaders are never done learning and always seek to improve themselves. They are curious about new possibilities.",
                             "Tell me about a time you learned something new that helped you do your job better",
                             Arrays.asList("Continuous learning", "Curiosity", "Self-improvement")),
            
            createPrincipleMap("Hire and Develop the Best",
                             "Leaders raise the performance bar with every hire and promotion. They recognize exceptional talent.",
                             "Tell me about a time you helped develop someone on your team",
                             Arrays.asList("Talent recognition", "Team development", "Mentoring")),
            
            createPrincipleMap("Insist on the Highest Standards",
                             "Leaders have relentlessly high standards. They continually raise the bar and drive their teams to deliver high quality products.",
                             "Tell me about a time you refused to compromise on quality",
                             Arrays.asList("High standards", "Quality focus", "Continuous improvement")),
            
            createPrincipleMap("Think Big",
                             "Thinking small is a self-fulfilling prophecy. Leaders create and communicate a bold direction that inspires results.",
                             "Tell me about a time you thought big and it paid off",
                             Arrays.asList("Bold vision", "Inspire others", "Scale thinking")),
            
            createPrincipleMap("Bias for Action",
                             "Speed matters in business. Many decisions and actions are reversible and do not need extensive study.",
                             "Tell me about a time you had to make a decision with incomplete information",
                             Arrays.asList("Move fast", "Calculated risks", "Reversible decisions")),
            
            createPrincipleMap("Frugality",
                             "Accomplish more with less. Constraints breed resourcefulness, self-sufficiency, and invention.",
                             "Tell me about a time you accomplished something with limited resources",
                             Arrays.asList("Resource optimization", "Cost consciousness", "Creative solutions")),
            
            createPrincipleMap("Earn Trust",
                             "Leaders listen attentively, speak candidly, and treat others respectfully. They are vocally self-critical.",
                             "Tell me about a time you had to earn someone's trust",
                             Arrays.asList("Transparency", "Reliability", "Self-awareness")),
            
            createPrincipleMap("Dive Deep",
                             "Leaders operate at all levels, stay connected to the details, audit frequently, and are skeptical when metrics and anecdotes differ.",
                             "Tell me about a time you dove deep to solve a problem",
                             Arrays.asList("Attention to detail", "Root cause analysis", "Data-driven decisions")),
            
            createPrincipleMap("Have Backbone; Disagree and Commit",
                             "Leaders are obligated to respectfully challenge decisions when they disagree, even when doing so is uncomfortable.",
                             "Tell me about a time you disagreed with your manager or team",
                             Arrays.asList("Respectful disagreement", "Stand up for beliefs", "Commit after decision")),
            
            createPrincipleMap("Deliver Results",
                             "Leaders focus on the key inputs for their business and deliver them with the right quality and in a timely fashion.",
                             "Tell me about a time you delivered results under pressure",
                             Arrays.asList("Results-oriented", "Meet deadlines", "Quality delivery")),
            
            createPrincipleMap("Strive to be Earth's Best Employer",
                             "Leaders work every day to create a safer, more productive, higher performing, more diverse, and more just work environment.",
                             "Tell me about a time you made your workplace better for others",
                             Arrays.asList("Employee experience", "Diversity and inclusion", "Safe workplace")),
            
            createPrincipleMap("Success and Scale Bring Broad Responsibility",
                             "We started in a garage, but we're not there anymore. We are big, we impact the world, and we are far from perfect.",
                             "Tell me about a time you took responsibility for a broader impact",
                             Arrays.asList("Social responsibility", "Global impact", "Continuous improvement"))
        );
        
        principles.put("principles", principlesList);
        
        return ResponseEntity.ok(principles);
    }
    
    @Operation(
        summary = "Get STAR method framework",
        description = "Returns the STAR method framework for structuring behavioral interview responses"
    )
    @GetMapping("/star-method")
    public ResponseEntity<Map<String, Object>> getSTARMethod() {
        Map<String, Object> star = new HashMap<>();
        
        star.put("title", "STAR Method Framework");
        star.put("description", "Structure your behavioral interview responses effectively");
        
        List<Map<String, Object>> components = Arrays.asList(
            createSTARComponentMap("Situation", "Set the context for your story", 
                                 Arrays.asList("Provide background", "Set the scene", "Give relevant details"),
                                 "In my previous role as a software engineer at XYZ Company..."),
            
            createSTARComponentMap("Task", "Describe what you needed to accomplish",
                                 Arrays.asList("Explain your responsibility", "Define the challenge", "Clarify your role"),
                                 "I was responsible for reducing the API response time by 50%..."),
            
            createSTARComponentMap("Action", "Explain the specific actions you took",
                                 Arrays.asList("Focus on what YOU did", "Be specific", "Highlight your skills"),
                                 "I analyzed the database queries, implemented caching, and optimized the algorithms..."),
            
            createSTARComponentMap("Result", "Share the outcomes of your actions",
                                 Arrays.asList("Quantify when possible", "Show impact", "Include lessons learned"),
                                 "As a result, we reduced response time by 60% and improved user satisfaction by 25%...")
        );
        
        star.put("components", components);
        
        Map<String, Object> tips = new HashMap<>();
        tips.put("preparation", Arrays.asList(
            "Prepare 8-10 STAR stories covering different situations",
            "Practice telling stories concisely (2-3 minutes each)",
            "Choose examples that showcase different skills",
            "Include both successes and failures with lessons learned"
        ));
        tips.put("delivery", Arrays.asList(
            "Be specific and avoid generalizations",
            "Focus on your individual contributions",
            "Use quantifiable results when possible",
            "Stay positive, even when discussing challenges"
        ));
        
        star.put("tips", tips);
        
        return ResponseEntity.ok(star);
    }
    
    @Operation(
        summary = "Get common behavioral questions",
        description = "Returns most frequently asked behavioral interview questions"
    )
    @GetMapping("/common-questions")
    public ResponseEntity<Map<String, Object>> getCommonBehavioralQuestions() {
        Map<String, Object> questions = new HashMap<>();
        
        questions.put("title", "Common Behavioral Interview Questions");
        questions.put("description", "Most frequently asked questions across all companies");
        
        Map<String, List<String>> categorizedQuestions = new HashMap<>();
        
        categorizedQuestions.put("Leadership", Arrays.asList(
            "Tell me about a time you led a team through a difficult situation",
            "Describe a time you had to influence someone without authority",
            "Give me an example of when you took initiative",
            "Tell me about a time you had to make a tough decision"
        ));
        
        categorizedQuestions.put("Problem Solving", Arrays.asList(
            "Describe a challenging problem you solved",
            "Tell me about a time you failed and what you learned",
            "Give me an example of when you had to think outside the box",
            "Describe a time you had to work with incomplete information"
        ));
        
        categorizedQuestions.put("Teamwork", Arrays.asList(
            "Tell me about a time you worked with a difficult team member",
            "Describe a time you had to collaborate with other teams",
            "Give me an example of when you helped a struggling colleague",
            "Tell me about a conflict you had with a coworker and how you resolved it"
        ));
        
        categorizedQuestions.put("Adaptability", Arrays.asList(
            "Tell me about a time you had to adapt to a major change",
            "Describe a time you had to learn something quickly",
            "Give me an example of when priorities changed suddenly",
            "Tell me about a time you had to work under pressure"
        ));
        
        categorizedQuestions.put("Communication", Arrays.asList(
            "Describe a time you had to explain something complex to a non-technical person",
            "Tell me about a time you had to give difficult feedback",
            "Give me an example of when you had to persuade someone",
            "Describe a presentation you gave that didn't go well"
        ));
        
        questions.put("categories", categorizedQuestions);
        questions.put("totalQuestions", categorizedQuestions.values().stream().mapToInt(List::size).sum());
        
        return ResponseEntity.ok(questions);
    }
    
    @Operation(
        summary = "Get behavioral interview tips",
        description = "Returns tips and best practices for behavioral interviews"
    )
    @GetMapping("/tips")
    public ResponseEntity<Map<String, Object>> getBehavioralInterviewTips() {
        Map<String, Object> tips = new HashMap<>();
        
        tips.put("title", "Behavioral Interview Success Tips");
        tips.put("description", "Best practices for acing behavioral interviews");
        
        Map<String, List<String>> tipCategories = new HashMap<>();
        
        tipCategories.put("Preparation", Arrays.asList(
            "Research the company's values and culture",
            "Prepare specific examples for each leadership principle",
            "Practice your stories out loud",
            "Prepare questions to ask the interviewer",
            "Review your resume and be ready to discuss any experience"
        ));
        
        tipCategories.put("During the Interview", Arrays.asList(
            "Listen carefully to the question",
            "Take a moment to think before answering",
            "Use the STAR method to structure responses",
            "Be specific and avoid generalizations",
            "Show enthusiasm and passion for your work"
        ));
        
        tipCategories.put("Common Mistakes to Avoid", Arrays.asList(
            "Being too vague or general in your examples",
            "Taking credit for team accomplishments",
            "Focusing too much on the situation and not enough on your actions",
            "Giving examples that are too old or irrelevant",
            "Speaking negatively about previous employers or colleagues"
        ));
        
        tipCategories.put("Follow-up", Arrays.asList(
            "Send a thank-you email within 24 hours",
            "Reiterate your interest in the position",
            "Address any concerns that came up during the interview",
            "Provide additional information if requested"
        ));
        
        tips.put("categories", tipCategories);
        
        return ResponseEntity.ok(tips);
    }
    
    // Helper methods
    private Map<String, Object> createPrincipleMap(String name, String description, String sampleQuestion, List<String> keyPoints) {
        Map<String, Object> principle = new HashMap<>();
        principle.put("name", name);
        principle.put("description", description);
        principle.put("sampleQuestion", sampleQuestion);
        principle.put("keyPoints", keyPoints);
        return principle;
    }
    
    private Map<String, Object> createSTARComponentMap(String component, String description, List<String> tips, String example) {
        Map<String, Object> starComponent = new HashMap<>();
        starComponent.put("component", component);
        starComponent.put("description", description);
        starComponent.put("tips", tips);
        starComponent.put("example", example);
        return starComponent;
    }
}