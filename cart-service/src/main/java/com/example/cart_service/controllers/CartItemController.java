package com.example.cart_service.controllers;


import com.example.cart_service.response.ApiResponse;
import com.example.cart_service.services.iservices.ICartItemService;
import com.example.cart_service.services.iservices.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cartItems")
public class CartItemController {


    private final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam(required = false) Long userId,
                                                     @RequestParam Integer quantity){
            if (cartId == null) {
                cartId = cartService.intializrCart(userId);
            }

         cartItemService.addItemToCart(userId,cartId,productId,quantity);

         return ResponseEntity.ok(new ApiResponse("cartId: ",cartId));

    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @RequestParam(required = false) Long userId,
                                                          @PathVariable Long productId){

            cartItemService.removeItemFromCart(userId,cartId,productId);
            return ResponseEntity.ok(new ApiResponse("remove item success",null));

    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @RequestParam(required = false) Long userId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer newQuantity){

            cartItemService.updateItemQuantity(userId,cartId,productId,newQuantity);
            return ResponseEntity.ok(new ApiResponse("update item quantity success",null));

    }
}
