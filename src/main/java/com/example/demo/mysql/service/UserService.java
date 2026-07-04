package com.example.demo.mysql.service;

import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mysql.dto.UserDtos.UserRequest;
import com.example.demo.mysql.dto.UserDtos.UserResponse;
import com.example.demo.mysql.entity.User;
import com.example.demo.mysql.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for user account operations.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Integer id) {
        return UserResponse.from(getUserOrThrow(id));
    }

    public UserResponse create(UserRequest request) {
        validateUserRequest(request);

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(request.passwordHash());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse update(Integer id, UserRequest request) {
        validateUserRequest(request);

        User user = getUserOrThrow(id);
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(request.passwordHash());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());

        return UserResponse.from(userRepository.save(user));
    }

    public void delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        try {
            userRepository.deleteUserById(id);
            userRepository.flush();
        } catch (RuntimeException ex) {
            String message = extractRootMessage(ex);
            if (message != null && message.contains("Active subscription")) {
                throw new ConflictException(message);
            }
            throw ex;
        }
    }

    private User getUserOrThrow(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    private void validateUserRequest(UserRequest request) {
        if (request.username() == null || request.username().isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (request.passwordHash() == null || request.passwordHash().isBlank()) {
            throw new IllegalArgumentException("passwordHash is required");
        }
    }

    private String extractRootMessage(Throwable ex) {
        Throwable cause = ex;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }
}
