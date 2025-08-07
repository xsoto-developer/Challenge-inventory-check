package com.walmarttech.inventory.exception;

public class AlmacenNotFoundException extends RuntimeException {
    public AlmacenNotFoundException(String message) {
        super(message);
    }
}
