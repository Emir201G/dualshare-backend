package com.app.dualsharebackend.dto;

import com.app.dualsharebackend.enums.MediaType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StoryResponseDTO {
    private String mediaUrl;
    private MediaType mediaType;
    private LocalDateTime expiresAt;

}
