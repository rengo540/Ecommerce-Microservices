package com.example.cart_service.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;


}
