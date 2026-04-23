package com.app.dualsharebackend.dto;

import com.app.dualsharebackend.enums.MediaType;
import lombok.Data;

@Data
public class StoryRequestDTO {
    private Long userId;
    private String mediaUrl;
    private MediaType mediaType;

}
