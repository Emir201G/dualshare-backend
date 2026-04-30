package com.app.dualsharebackend.service;

import com.app.dualsharebackend.model.Story;
import com.app.dualsharebackend.repository.StoryRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryCleanupService {

    private final StoryRepository storyRepository;
    private final Cloudinary cloudinary;

    public StoryCleanupService(StoryRepository storyRepository,
                               Cloudinary cloudinary) {
        this.storyRepository = storyRepository;
        this.cloudinary = cloudinary;
    }

    @Scheduled(fixedRate = 10000) // cada 10 seg (testing)
    public void deleteExpiredStories() {

        List<Story> expiredStories =
                storyRepository.findByExpiresAtBefore(LocalDateTime.now());

        for (Story story : expiredStories) {
            try {

                String resourceType = story.getMediaType().name().equals("VIDEO")
                        ? "video"
                        : "image";

                cloudinary.uploader().destroy(
                        story.getPublicId(),
                        ObjectUtils.asMap("resource_type", resourceType)
                );

                storyRepository.delete(story);

            } catch (Exception e) {
                System.out.println("Error deleting story: " + e.getMessage());
            }
        }
    }
}