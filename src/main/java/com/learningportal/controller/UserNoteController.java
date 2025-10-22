package com.learningportal.controller;

import com.learningportal.model.UserNote;
import com.learningportal.service.UserNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
@Tag(name = "User Notes", description = "API for managing user notes and annotations")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNoteController {

    private final UserNoteService noteService;

    public UserNoteController(UserNoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(
        summary = "Create a new note",
        description = "Create a new note with rich text content and HTML sanitization"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Note created successfully",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = UserNote.class))),
        @ApiResponse(responseCode = "400", description = "Invalid note data")
    })
    @PostMapping
    public ResponseEntity<UserNote> createNote(
            @Parameter(description = "Note data", required = true)
            @Valid @RequestBody UserNote note) {
        
        UserNote createdNote = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @Operation(
        summary = "Get note by ID",
        description = "Retrieve a specific note by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Note found"),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserNote> getNoteById(
            @Parameter(description = "Note ID", required = true, example = "1")
            @PathVariable Long id) {
        
        Optional<UserNote> note = noteService.getNoteById(id);
        return note.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Get user notes",
        description = "Retrieve all notes for a specific user with pagination"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notes retrieved successfully")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UserNote>> getUserNotes(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<UserNote> notes = noteService.getUserNotes(userId, pageable);
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Get module notes",
        description = "Retrieve all notes for a specific learning module"
    )
    @GetMapping("/module/{moduleId}/user/{userId}")
    public ResponseEntity<List<UserNote>> getModuleNotes(
            @Parameter(description = "Module ID", required = true, example = "1")
            @PathVariable Long moduleId,
            
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        List<UserNote> notes = noteService.getModuleNotes(moduleId, userId);
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Get topic notes",
        description = "Retrieve all notes for a specific topic"
    )
    @GetMapping("/topic/{topicId}/user/{userId}")
    public ResponseEntity<List<UserNote>> getTopicNotes(
            @Parameter(description = "Topic ID", required = true, example = "1")
            @PathVariable Long topicId,
            
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        List<UserNote> notes = noteService.getTopicNotes(topicId, userId);
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Get notes by category",
        description = "Retrieve notes filtered by category"
    )
    @GetMapping("/category/{category}/user/{userId}")
    public ResponseEntity<List<UserNote>> getNotesByCategory(
            @Parameter(description = "Note category", required = true)
            @PathVariable UserNote.Category category,
            
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        List<UserNote> notes = noteService.getNotesByCategory(category, userId);
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Get public notes",
        description = "Retrieve all publicly shared notes"
    )
    @GetMapping("/public")
    public ResponseEntity<List<UserNote>> getPublicNotes() {
        List<UserNote> notes = noteService.getPublicNotes();
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Search user notes",
        description = "Search through user's notes by title and content"
    )
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<UserNote>> searchUserNotes(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            
            @Parameter(description = "Search query", required = true)
            @RequestParam String q) {
        
        List<UserNote> notes = noteService.searchUserNotes(userId, q);
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Search public notes",
        description = "Search through public notes by title and content"
    )
    @GetMapping("/search/public")
    public ResponseEntity<List<UserNote>> searchPublicNotes(
            @Parameter(description = "Search query", required = true)
            @RequestParam String q) {
        
        List<UserNote> notes = noteService.searchPublicNotes(q);
        return ResponseEntity.ok(notes);
    }

    @Operation(
        summary = "Update note",
        description = "Update an existing note with new content"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Note updated successfully"),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserNote> updateNote(
            @Parameter(description = "Note ID", required = true, example = "1")
            @PathVariable Long id,
            
            @Parameter(description = "Updated note data", required = true)
            @Valid @RequestBody UserNote noteUpdate) {
        
        try {
            UserNote updatedNote = noteService.updateNote(id, noteUpdate);
            return ResponseEntity.ok(updatedNote);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Delete note",
        description = "Delete a note permanently"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Note deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Note not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(
            @Parameter(description = "Note ID", required = true, example = "1")
            @PathVariable Long id) {
        
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}