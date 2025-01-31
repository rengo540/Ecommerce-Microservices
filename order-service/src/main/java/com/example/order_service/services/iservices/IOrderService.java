package com.example.order_service.services.iservices;



import com.example.order_service.dtos.OrderDto;
import com.example.order_service.models.Order;
import com.example.order_service.models.enums.OrderStatus;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId,Long cartId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    Order changeOrderState(Long orderId, String orderStatus);
    OrderDto convertToDto(Order order);

}
