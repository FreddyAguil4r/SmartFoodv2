package com.spring.implementation.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message, int id) {
        super(message + " with id " + id + " not found");
    }
}
