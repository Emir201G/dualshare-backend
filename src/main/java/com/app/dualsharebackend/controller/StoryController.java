package com.app.dualsharebackend.controller;

import com.app.dualsharebackend.dto.StoryRequestDTO;
import com.app.dualsharebackend.dto.StoryResponseDTO;
import com.app.dualsharebackend.service.CloudinaryService;
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
    private final CloudinaryService cloudinaryService;

    public StoryController(StoryService storyService, CloudinaryService cloudinaryService) {
        this.storyService = storyService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<StoryResponseDTO> uploadStory(@RequestBody StoryRequestDTO storyRequestDTO) {
        try {
            StoryResponseDTO story = storyService.uploadStory(storyRequestDTO);
            return ResponseEntity.ok(story);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(url);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir archivo: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponseDTO>> getStories(@PathVariable Long userId) {
        try {
            List<StoryResponseDTO> stories = storyService.getActiveStories(userId);
            return ResponseEntity.ok(stories);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

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