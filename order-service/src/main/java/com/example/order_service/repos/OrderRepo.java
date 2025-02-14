package com.example.order_service.repos;

import com.example.order_service.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends MongoRepository<Order,String> {
    List<Order> findByUserId(Long userId);


}
