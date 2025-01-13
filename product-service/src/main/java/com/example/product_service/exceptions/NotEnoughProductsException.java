package com.example.product_service.exceptions;

public class NotEnoughProductsException extends RuntimeException{
    public NotEnoughProductsException(String message){
        super(message);
    }

}
