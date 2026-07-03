package com.ecommerce.authservice.dto;

import com.ecommerce.authservice.model.User;
import com.ecommerce.authservice.model.UserRole;

public record UserResponse(
        Long id,
        String email,
        UserRole role
) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
