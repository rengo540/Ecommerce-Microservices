package com.example.auth_service.dtos;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;

}
