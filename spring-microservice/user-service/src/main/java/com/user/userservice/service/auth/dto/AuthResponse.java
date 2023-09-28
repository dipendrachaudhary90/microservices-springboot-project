package com.user.userservice.service.auth.dto;

import lombok.Data;
import com.user.userservice.model.User;

@Data
public class AuthResponse {
    private String accessToken;
    private String expiredIn;
    private String refreshToken;
    private User user;
}
