package com.example.order_service.exceptions;

public class NotEnoughProductsException extends RuntimeException{
    public NotEnoughProductsException(String message){
        super(message);
    }

}
