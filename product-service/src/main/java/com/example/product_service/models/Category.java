package com.example.product_service.models;

import com.example.product_service.models.auditing.BaseEntityAuditing;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Category extends BaseEntityAuditing {

    private String name ;


    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> product ;

    public Category(String name) {
        this.name=name;
    }
}
