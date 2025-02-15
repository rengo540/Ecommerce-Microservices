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
                                                     @RequestHeader(value = "userId", required = false, defaultValue ="") String userId,
                                                     @RequestParam Integer quantity){
            Long user_id=null;
            if(!userId.isEmpty()){
                user_id = Long.valueOf(userId);
            }
            if (cartId == null) {
                cartId = cartService.intializrCart(user_id);
            }

        cartItemService.addItemToCart(user_id,cartId,productId,quantity);

         return ResponseEntity.ok(new ApiResponse("cartId: ",cartId));

    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @RequestHeader(value = "userId", required = false, defaultValue ="") String userId,
                                                          @PathVariable Long productId){
            Long user_id=null;
            if(!userId.isEmpty()){
                user_id = Long.valueOf(userId);
            }
            cartItemService.removeItemFromCart(user_id,cartId,productId);
            return ResponseEntity.ok(new ApiResponse("remove item success",null));

    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @RequestHeader(value = "userId", required = false, defaultValue ="") String userId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer newQuantity){

            Long user_id=null;
            if(!userId.isEmpty()){
                user_id = Long.valueOf(userId);
            }
            cartItemService.updateItemQuantity(user_id,cartId,productId,newQuantity);
            return ResponseEntity.ok(new ApiResponse("update item quantity success",null));

    }
}
