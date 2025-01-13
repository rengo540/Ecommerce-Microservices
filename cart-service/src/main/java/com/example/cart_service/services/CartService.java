package com.example.cart_service.services;

import com.example.cart_service.exceptions.ResourceNotFoundException;
import com.example.cart_service.models.Cart;
import com.example.cart_service.models.CartItem;
import com.example.cart_service.repos.CartRepo;
import com.example.cart_service.services.iservices.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@AllArgsConstructor
@Service
public class CartService implements ICartService {

    private final CartRepo cartRepo;
    private final NextSequenceService nextSequenceService;
    private final RedisService redisService;


    @Override
    public Cart getCart(Long id,Long userId) {
        Cart cart;
        if(userId==null){
            //unauthenticatd user
            cart=(Cart) redisService.get(id.toString());
        }else{
            //authenticated user
            cart = cartRepo.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
            BigDecimal totalAmount = cart.getItems().stream()
                    .map(CartItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            cart.setTotalAmount(totalAmount);
            cart= cartRepo.save(cart);
        }
        return cart;
    }

    @Override
    public void clearCart(Long id) {
        cartRepo.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id,Long userId) {
        Cart cart = getCart(id,userId);
        return cart.getTotalAmount();
    }

    @Override
    public Long intializrCart(Long userId){
        Cart cart =new Cart();
        cart.setId(nextSequenceService.getNextSequence("counter"));
        if(userId==null){
            redisService.save(cart.getId()+"",cart);
            return cart.getId();
        }else {
            cart.setUserId(userId);
            return cartRepo.save(cart).getId();
        }
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepo.findByUserId(userId);
    }


}
