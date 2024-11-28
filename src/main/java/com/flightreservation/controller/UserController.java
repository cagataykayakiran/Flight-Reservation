package com.flightreservation.controller;

import com.flightreservation.common.ApiResponse;
import com.flightreservation.dto.request.UserRequest;
import com.flightreservation.dto.response.UserResponse;
import com.flightreservation.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User created successfully")
                .data(userResponse)
                .build());
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(
            @PathVariable Long userId,
            @Valid@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(
                userId,
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User updated successfully")
                .data(userResponse)
                .build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User deleted successfully")
                .build());
    }
}

