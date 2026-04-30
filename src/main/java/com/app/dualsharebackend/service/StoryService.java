package com.app.dualsharebackend.service;

import com.app.dualsharebackend.dto.StoryResponseDTO;
import com.app.dualsharebackend.enums.MediaType;
import com.app.dualsharebackend.exception.StoryNotFoundException;
import com.app.dualsharebackend.mapper.StoryMapper;
import com.app.dualsharebackend.model.Story;
import com.app.dualsharebackend.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final CloudinaryService cloudinaryService;

    public StoryService(StoryRepository storyRepository,
                        StoryMapper storyMapper,
                        CloudinaryService cloudinaryService) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
        this.cloudinaryService = cloudinaryService;
    }

    public StoryResponseDTO uploadStory(MultipartFile file,
                                        Long userId,
                                        String mediaType) {

        try {
            Map result = cloudinaryService.uploadFile(file);

            Story story = new Story();
            story.setUserId(userId);
            story.setMediaType(MediaType.valueOf(mediaType));
            story.setMediaUrl(result.get("url").toString());
            story.setPublicId(result.get("public_id").toString());

            LocalDateTime now = LocalDateTime.now();
            story.setCreatedAt(now);
            story.setExpiresAt(now.plusSeconds(30));

            storyRepository.save(story);

            return storyMapper.toDTO(story);

        } catch (Exception e) {
            throw new RuntimeException("Error subiendo story: " + e.getMessage());
        }
    }

    public List<StoryResponseDTO> getActiveStories(Long userId) {
        List<Story> stories = storyRepository
                .findByUserIdAndDeletedAtIsNullAndExpiresAtAfter(
                        userId,
                        LocalDateTime.now()
                );

        return storyMapper.toDTOList(stories);
    }

    public void deleteStory(Long storyId) {

        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new StoryNotFoundException(storyId));

            String resourceType = story.getMediaType() == MediaType.VIDEO
                    ? "video"
                    : "image";

            cloudinaryService.deleteFile(story.getPublicId(),resourceType);


        story.setDeletedAt(LocalDateTime.now());
        storyRepository.save(story);
    }
}