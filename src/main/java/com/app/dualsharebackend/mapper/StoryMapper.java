package com.app.dualsharebackend.mapper;

import com.app.dualsharebackend.dto.StoryRequestDTO;
import com.app.dualsharebackend.dto.StoryResponseDTO;
import com.app.dualsharebackend.model.Story;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    Story toEntity(StoryRequestDTO storyRequestDTO);
    StoryResponseDTO toDTO(Story story);

    List<StoryResponseDTO> toDTOList(List<Story> stories);
}
