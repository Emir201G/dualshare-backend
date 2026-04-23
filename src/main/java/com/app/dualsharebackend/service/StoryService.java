package com.app.dualsharebackend.service;

import com.app.dualsharebackend.dto.StoryRequestDTO;
import com.app.dualsharebackend.dto.StoryResponseDTO;
import com.app.dualsharebackend.mapper.StoryMapper;
import com.app.dualsharebackend.mapper.UserMapper;
import com.app.dualsharebackend.model.Story;
import com.app.dualsharebackend.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    public StoryService(StoryRepository storyRepository, StoryMapper storyMapper) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
    }

    public StoryResponseDTO uploadStory(StoryRequestDTO storyRequestDTO) {

        Story story = storyMapper.toEntity(storyRequestDTO);
        LocalDateTime now = LocalDateTime.now();

        story.setCreatedAt(now);
        story.setExpiresAt(now.plusHours(24));

        storyRepository.save(story);

        return storyMapper.toDTO(story);
    }

    public List<StoryResponseDTO> getActiveStories(Long userId) {
        List<Story> stories = storyRepository.findByUserIdAndDeletedAtIsNullAndExpiresAtAfter(
                userId,
                LocalDateTime.now()
        );
        return storyMapper.toDTOList(stories);
    }

    public void deleteStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        story.setDeletedAt(LocalDateTime.now());
        storyRepository.save(story);
    }

}