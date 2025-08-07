package com.walmarttech.inventory.exception;

public class ProductoNotFoundException  extends RuntimeException {
    public ProductoNotFoundException(String message) {
        super(message);
    }
}
