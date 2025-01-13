package com.example.cart_service.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "counter")
public class CounterSequences {
    @Id
    private String id;
    private int seq;

// getters and setters
}