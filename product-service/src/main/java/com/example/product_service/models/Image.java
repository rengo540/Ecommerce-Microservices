package com.example.product_service.models;

import com.example.product_service.models.auditing.BaseEntityAuditing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "imagePath" }))
public class Image extends BaseEntityAuditing {

    private  String fileName ;
    private String fileType;
    private String imagePath;

    @ManyToOne()
    @JoinColumn(name="product_id")
    private Product product;


    public Image(){}

}
