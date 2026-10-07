package com.example.productapi.common;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String m) {
        super(m);
    }
}
