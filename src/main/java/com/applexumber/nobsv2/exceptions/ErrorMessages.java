package com.applexumber.nobsv2.exceptions;

public enum ErrorMessages {
    PRODUCT_NOT_FOUND("Product not found"),
    NAME_REQUIRED("Name is required."),
    DESCRIPTION_LENGHT("Description must be at least 20 characters."),
    PRICE_CANNOT_BE_NEGATIVE("Price cannot be negative.");


    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
