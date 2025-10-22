package com.learningportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth/social")
@Tag(name = "Social Authentication", description = "OAuth2 social login integration")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SocialAuthController {

    @Operation(
        summary = "Initiate Google OAuth2 login",
        description = "Redirect to Google OAuth2 authorization endpoint"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "302", description = "Redirect to Google OAuth2"),
        @ApiResponse(responseCode = "500", description = "OAuth2 configuration error")
    })
    @GetMapping("/google")
    public ResponseEntity<Map<String, String>> initiateGoogleLogin() {
        // OAuth2 redirect URL would be handled by Spring Security OAuth2 client
        Map<String, String> response = Map.of(
            "authUrl", "/oauth2/authorization/google",
            "provider", "google"
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Initiate GitHub OAuth2 login",
        description = "Redirect to GitHub OAuth2 authorization endpoint"
    )
    @GetMapping("/github")
    public ResponseEntity<Map<String, String>> initiateGitHubLogin() {
        Map<String, String> response = Map.of(
            "authUrl", "/oauth2/authorization/github",
            "provider", "github"
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Initiate LinkedIn OAuth2 login",
        description = "Redirect to LinkedIn OAuth2 authorization endpoint"
    )
    @GetMapping("/linkedin")
    public ResponseEntity<Map<String, String>> initiateLinkedInLogin() {
        Map<String, String> response = Map.of(
            "authUrl", "/oauth2/authorization/linkedin",
            "provider", "linkedin"
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Handle OAuth2 callback",
        description = "Process OAuth2 callback and create/link user account"
    )
    @PostMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleOAuth2Callback(
            @Parameter(description = "OAuth2 callback data", required = true)
            @RequestBody Map<String, Object> callbackData) {
        
        // This would typically be handled by Spring Security OAuth2 client
        // Here we provide a placeholder response
        Map<String, Object> response = Map.of(
            "success", true,
            "message", "OAuth2 authentication successful",
            "user", Map.of(
                "id", 1L,
                "email", "user@example.com",
                "provider", callbackData.getOrDefault("provider", "unknown")
            )
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Link social account",
        description = "Link a social media account to existing user account"
    )
    @PostMapping("/link")
    public ResponseEntity<Map<String, Object>> linkSocialAccount(
            @Parameter(description = "User ID", required = true)
            @RequestParam Long userId,
            
            @Parameter(description = "Social provider", required = true)
            @RequestParam String provider,
            
            @Parameter(description = "Social account data", required = true)
            @RequestBody Map<String, Object> socialData) {
        
        Map<String, Object> response = Map.of(
            "success", true,
            "message", "Social account linked successfully",
            "linkedProvider", provider,
            "userId", userId
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Unlink social account",
        description = "Remove social media account link from user account"
    )
    @DeleteMapping("/unlink")
    public ResponseEntity<Map<String, Object>> unlinkSocialAccount(
            @Parameter(description = "User ID", required = true)
            @RequestParam Long userId,
            
            @Parameter(description = "Social provider", required = true)
            @RequestParam String provider) {
        
        Map<String, Object> response = Map.of(
            "success", true,
            "message", "Social account unlinked successfully",
            "unlinkedProvider", provider,
            "userId", userId
        );
        
        return ResponseEntity.ok(response);
    }
}