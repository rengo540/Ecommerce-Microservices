package com.example.auth_service.controllers;


import com.example.auth_service.dtos.CreateUserRequest;
import com.example.auth_service.dtos.UserDto;
import com.example.auth_service.dtos.UserUpdateRequest;
import com.example.auth_service.exceptions.ResourceNotFoundException;
import com.example.auth_service.models.User;
import com.example.auth_service.response.ApiResponse;
import com.example.auth_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));

    }
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));

    }

}
