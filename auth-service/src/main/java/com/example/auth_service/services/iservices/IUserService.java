package com.example.auth_service.services.iservices;


import com.example.auth_service.dtos.CreateUserRequest;
import com.example.auth_service.dtos.UserDto;
import com.example.auth_service.dtos.UserUpdateRequest;
import com.example.auth_service.models.User;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
