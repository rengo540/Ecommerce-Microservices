package com.example.cart_service.controllers;


import com.example.cart_service.models.Cart;
import com.example.cart_service.response.ApiResponse;
import com.example.cart_service.services.iservices.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {


    private final ICartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId,
                                               @RequestHeader(value = "userId", required = false, defaultValue ="") String userId
                                               ){
            Long user_id=null;
             if(!userId.isEmpty()){
                 user_id = Long.valueOf(userId);
             }
           Cart cart = cartService.getCart(cartId,user_id);
           return ResponseEntity.ok(new ApiResponse("success",cart));

    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){

            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("clear success",null));

    }

    @GetMapping("/{cartId}/totalprice")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable  Long cartId
                                                    , @RequestHeader(value = "userId", required = false, defaultValue ="") String userId){
        Long user_id=null;
        if(!userId.isEmpty()){
            user_id = Long.valueOf(userId);
        }
            BigDecimal totalPrice = cartService.getTotalPrice(cartId,user_id);
            return  ResponseEntity.ok(new ApiResponse("total price",totalPrice));

    }
}
