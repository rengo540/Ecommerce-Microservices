package com.example.cart_service.services.iservices;


import com.example.cart_service.models.CartItem;

public interface ICartItemService {

    void addItemToCart(Long userId,Long cartId,Long productId,int quantity);
    void removeItemFromCart(Long userId,Long cartId,Long productId);
    void updateItemQuantity(Long userId,Long cartId,Long productId,int quantity);
    CartItem getCartItem(Long userId,Long cartId, Long productId);
}
