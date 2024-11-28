package com.flightreservation.service.user;

import com.flightreservation.dto.response.UserResponse;
import com.flightreservation.entity.User;

import java.util.List;

public interface IUserService {
    UserResponse createUser(String firstName, String lastName, String email, String password);

    User findUserById(Long userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long userId, String firstName, String lastName, String email, String password);

    void deleteUser(Long userId);

    UserResponse getUser(Long userId);
}
