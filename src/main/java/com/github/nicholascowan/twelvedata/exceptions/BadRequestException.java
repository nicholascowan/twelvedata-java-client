package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 400 Bad Request error.
 */
public class BadRequestException extends TwelveDataException {
    
    public BadRequestException(String message) {
        super(message, 400);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, 400, cause);
    }
} 