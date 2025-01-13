package com.example.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiCartResponse {
    private String status;
    private String message ;
    private CartResponse data;
}
