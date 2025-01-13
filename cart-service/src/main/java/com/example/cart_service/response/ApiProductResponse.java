package com.example.cart_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiProductResponse {
    private String status;
    private String message ;
    private  ProductResponse data;
}
