package com.lopez.ecommerce_api.exception;

public class OutOfStockException extends Exception {

    public OutOfStockException(String productName, int stock, int requested) {
        super("Product " + productName + " has only " + stock + " stock left. " + "REQUESTED: " + requested);
    }
}
