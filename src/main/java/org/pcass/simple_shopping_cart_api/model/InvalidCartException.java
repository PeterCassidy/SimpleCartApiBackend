package org.pcass.simple_shopping_cart_api.model;

public class InvalidCartException extends RuntimeException {
    public InvalidCartException(String message) {
        super(message);
    }
}
