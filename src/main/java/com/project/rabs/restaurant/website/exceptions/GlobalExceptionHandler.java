package com.project.rabs.restaurant.website.exceptions;

import com.project.rabs.restaurant.website.dto.ErrorResponse;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    // Menu Exceptions
    @ExceptionHandler(MenuServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMenuServiceException(MenuServiceException e) {
        logger.error("Menu service error {}", e.getMessage(), e);
        return new ErrorResponse("MENU_SERVICE_ERROR", e.getMessage());
    }

    @ExceptionHandler(MenuItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMenuItemNotFoundException(MenuItemNotFoundException e) {
        logger.warn("Menu item not found {}", e.getMessage(), e);
        return new ErrorResponse("MENU_ITEM_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(DuplicateMenuItemException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateMenuItemException(DuplicateMenuItemException e) {
        logger.warn("Duplicate menu item {}", e.getMessage(), e);
        return new ErrorResponse("DUPLICATE_MENU_ITEM", e.getMessage());
    }

    // Generic exceptions
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(BadRequestException e) {
        logger.warn("Invalid argument {}", e.getMessage(), e);
        return new ErrorResponse("INVALID_ARGUMENT", e.getMessage());
    }

}
