package com.example.productservice.exception;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(){};

    public ProductNotFoundException(String message){
        super(message);
    }
}
