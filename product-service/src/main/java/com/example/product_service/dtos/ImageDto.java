package com.example.product_service.dtos;

import lombok.Data;

@Data
public class ImageDto {
    private Long id ;
    private  String fileName ;
    private String fileType;
    private String imagePath;

}

