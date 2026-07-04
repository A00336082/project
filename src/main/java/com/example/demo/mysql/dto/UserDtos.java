package com.example.demo.mysql.dto;

import com.example.demo.mysql.entity.User;

import java.time.LocalDateTime;

public final class UserDtos {

    private UserDtos() {
    }

    public record UserRequest(
            String username,
            String email,
            String passwordHash,
            String firstName,
            String lastName
    ) {
    }

    public record UserResponse(
            Integer userId,
            String username,
            String email,
            String firstName,
            String lastName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static UserResponse from(User user) {
            return new UserResponse(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
        }
    }
}
