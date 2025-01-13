package com.example.order_service.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem  {

    private  int quantity;
    private BigDecimal price;
    private long productId;

    public OrderItem(int quantity, BigDecimal price, long productId) {
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }
}
