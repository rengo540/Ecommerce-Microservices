package com.example.cart_service.services;


import com.example.cart_service.exceptions.ResourceNotFoundException;
import com.example.cart_service.models.Cart;
import com.example.cart_service.models.CartItem;
import com.example.cart_service.repos.CartRepo;
import com.example.cart_service.response.ProductResponse;
import com.example.cart_service.services.iservices.ICartItemService;
import com.example.cart_service.services.remote.ProductServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@AllArgsConstructor
@Service
public class CartItemService implements ICartItemService {

    private final CartService cartService;
    private final CartRepo cartRepo;
    private final ProductServiceClient productServiceClient;
    private final RedisService redisService;

    @Override
    public void addItemToCart(Long userId,Long cartId, Long productId, int quantity) {
        //get the cart
        Cart cart = cartService.getCart(cartId,userId);
        //get the product
        ProductResponse product = productServiceClient.getProductById(productId);

        //check if the product is already in the cart
        //if yes ,then increase the quantity
        //if no ,initiate a new cartItem and save it
        CartItem cartItem= cart.getCartItemIfExist(productId).orElse(new CartItem());
        if (cartItem.getProductId()>0){
            cartItem.setQuantity(quantity+cartItem.getQuantity());
        }else{
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem); //update totalAmount inside this.
        if(userId !=null){
            cartRepo.save(cart);
        }else {
            redisService.save(cart.getId()+"",cart);
        }
    }

    @Override
    public void removeItemFromCart(Long userId,Long cartId, Long productId) {
        Cart cart =cartService.getCart(cartId,userId);
        CartItem cartItem = cart.getCartItemIfExist(productId).orElseThrow(()->new ResourceNotFoundException("this product is not in cart"));
        cart.removeItem(cartItem);
        if(userId !=null){
            cartRepo.save(cart);
        }else {
            redisService.save(cart.getId()+"",cart);
        }
    }

    @Override
    public void updateItemQuantity(Long userId,Long cartId, Long productId, int newQuantity) {
        //get the cart
        Cart cart = cartService.getCart(cartId,userId);
        if(cart==null){
            throw new ResourceNotFoundException("cart not found ");
        }
        //get the product
        ProductResponse product = productServiceClient.getProductById(productId);

        cart.getCartItemIfExist(productId)
                .ifPresent(item ->{
                    item.setQuantity(newQuantity);
                    item.setUnitPrice(product.getPrice());
                    item.setTotalPrice();
                });
        cart.updateTotalAmount();
        if(userId !=null){
            cartRepo.save(cart);
        }else {
            redisService.save(cart.getId()+"",cart);
        }
    }

    @Override
    public CartItem getCartItem(Long userId,Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId,userId);
        return cart.getCartItemIfExist(productId)
                .orElseThrow(()->new ResourceNotFoundException("item not found"));
    }
}
