package com.example.cart_service.repos;

import com.example.cart_service.models.Cart;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepo extends MongoRepository<Cart,Long> {

    Cart findByUserId(Long userId);
}
