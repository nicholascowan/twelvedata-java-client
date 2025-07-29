package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 401 Unauthorized error.
 * 
 * <p>This indicates that an invalid or incorrect API key was provided.
 * Verify your API key is correct and sign up for a key if needed.</p>
 */
public class UnauthorizedException extends TwelveDataException {
    
    public UnauthorizedException(String message) {
        super(message, 401);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, 401, cause);
    }
} 