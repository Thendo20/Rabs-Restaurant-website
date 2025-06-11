package com.project.rabs.restaurant.website.exceptions;

public class DuplicateMenuItemException extends RuntimeException {
    public DuplicateMenuItemException(String message) {
        super(message);
    }
}
