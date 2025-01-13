package com.example.product_service.dtos;



import com.example.product_service.models.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OneProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}