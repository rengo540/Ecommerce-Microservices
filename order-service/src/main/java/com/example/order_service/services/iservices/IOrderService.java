package com.example.order_service.services.iservices;



import com.example.order_service.dtos.OrderDto;
import com.example.order_service.models.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId,Long cartId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
