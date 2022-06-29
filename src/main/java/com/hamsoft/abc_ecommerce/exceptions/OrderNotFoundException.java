package com.hamsoft.abc_ecommerce.exceptions;

public class OrderNotFoundException  extends Exception{

    public OrderNotFoundException() {
        super();
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
