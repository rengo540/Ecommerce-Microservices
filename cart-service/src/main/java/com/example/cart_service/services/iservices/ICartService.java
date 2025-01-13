package com.example.cart_service.services.iservices;



import com.example.cart_service.models.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id,Long userId);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id,Long userId);


    Long intializrCart(Long userId);

    Cart getCartByUserId(Long userId);
}
