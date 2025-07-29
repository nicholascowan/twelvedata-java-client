package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 400 Bad Request error.
 * 
 * <p>This indicates that invalid or incorrect parameter(s) were provided.
 * Check the message in the response for details and refer to the API Documentation
 * to correct the input.</p>
 */
public class BadRequestException extends TwelveDataException {
    
    public BadRequestException(String message) {
        super(message, 400);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, 400, cause);
    }
} 