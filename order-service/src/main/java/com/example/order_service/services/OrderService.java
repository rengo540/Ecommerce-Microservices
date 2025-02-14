package com.example.order_service.services;


import com.example.order_service.dtos.OrderDto;
import com.example.order_service.exceptions.NotEnoughProductsException;
import com.example.order_service.exceptions.ResourceNotFoundException;
import com.example.order_service.models.Order;
import com.example.order_service.models.OrderItem;
import com.example.order_service.models.enums.OrderStatus;
import com.example.order_service.remote.CartServiceClient;
import com.example.order_service.remote.ProductServiceClient;
import com.example.order_service.repos.OrderRepo;
import com.example.order_service.response.CartResponse;
import com.example.order_service.response.OrderUpdatesResponse;
import com.example.order_service.response.ProductResponse;
import com.example.order_service.services.iservices.IOrderService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService implements IOrderService {

    private final OrderRepo orderRepo;
    private final CartServiceClient cartServiceClient;
    private final ProductServiceClient productServiceClient;
    private final ModelMapper modelMapper;
    private final NextSequenceService nextSequenceService;
    private final KafkaTemplate<String, OrderUpdatesResponse> kafkaTemplate;
    @Override
    public OrderDto getOrder(String orderId) {
        kafkaTemplate.send("order-updates",new OrderUpdatesResponse("0",
               " order.getOrderStatus().toString()",
                5));
        return orderRepo.findById(orderId).map(this::convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }


    @Transactional
    @Override
    public Order placeOrder(Long userId,Long cartId) {
        //******************
        CartResponse cart = cartServiceClient.getCart(cartId,userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calcTotalAmount(orderItems));
        Order savedOrder = orderRepo.save(order);
        cartServiceClient.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(CartResponse cart){
        Order order = new Order();
        order.setId(nextSequenceService.getNextSequence("counter"));
        order.setUserId(cart.getUserId());
        order.setOrderStatus(OrderStatus.PENDENG);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, CartResponse cart){
        return cart.getItems().stream().
                map(cartItem -> {
                    ProductResponse product = productServiceClient.getProductById(cartItem.getProductId());
                    int inventory = product.getInventory();
                    if(inventory < cartItem.getQuantity()){
                        throw new NotEnoughProductsException("the is not enough quantity");
                    }
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productServiceClient.saveProduct(product.getId(),product);

                    return  new OrderItem(
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice(),
                            product.getId()
                    );
                }).toList();

    }
    private BigDecimal calcTotalAmount(List<OrderItem> orderItemList){
        return orderItemList.stream().map(item ->
                item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        return orderRepo.findByUserId(userId).stream().map(this::convertToDto)
                .toList();
    }

    @Override
    public Order changeOrderState(String orderId,String orderStatusStr) {
        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusStr.toUpperCase());
       Order order = orderRepo.findById(orderId).orElseThrow(NotFoundException::new);
       order.setOrderStatus(orderStatus);
       order = orderRepo.save(order);
       kafkaTemplate.send("order-updates",new OrderUpdatesResponse(order.getId(),
               order.getOrderStatus().toString(),
               order.getUserId()));
       return order;
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
