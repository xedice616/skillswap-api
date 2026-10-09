package com.xadice.skillswap.service;

import com.xadice.skillswap.dto.request.UserRequest;
import com.xadice.skillswap.dto.response.UserResponse;
import com.xadice.skillswap.exception.DuplicateResourceException;
import com.xadice.skillswap.exception.ResourceNotFoundException;
import com.xadice.skillswap.model.User;
import com.xadice.skillswap.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "A user with this email already exists"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setBio(request.getBio());

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );

        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getBio()
        );
    }
}