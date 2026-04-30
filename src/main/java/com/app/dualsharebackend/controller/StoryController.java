package com.app.dualsharebackend.controller;

import com.app.dualsharebackend.dto.StoryResponseDTO;
import com.app.dualsharebackend.service.StoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    // SUBIR STORY (ARCHIVO)
    @PostMapping("/upload-file")
    public ResponseEntity<StoryResponseDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long userId,
            @RequestParam String mediaType
    ) {
        try {
            StoryResponseDTO story = storyService.uploadStory(file, userId, mediaType);
            return ResponseEntity.ok(story);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // OBTENER STORIES ACTIVAS
    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponseDTO>> getStories(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(storyService.getActiveStories(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // ELIMINAR STORY
    @DeleteMapping("/{storyId}")
    public ResponseEntity<String> deleteStory(@PathVariable Long storyId) {
        try {
            storyService.deleteStory(storyId);
            return ResponseEntity.ok("Historia eliminada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar historia");
        }
    }
}