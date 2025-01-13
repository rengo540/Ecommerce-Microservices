package com.example.auth_service.dtos;

import lombok.Data;

@Data
public class    CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
