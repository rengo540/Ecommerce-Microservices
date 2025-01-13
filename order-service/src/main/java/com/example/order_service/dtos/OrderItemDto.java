package com.example.order_service.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
