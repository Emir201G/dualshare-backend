package com.app.dualsharebackend.dto;

import lombok.Data;

@Data
public class AddFriendRequest {
    private Long userId;
    private String code;

}
