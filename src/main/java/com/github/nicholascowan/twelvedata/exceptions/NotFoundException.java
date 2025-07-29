package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 404 Not Found error.
 * 
 * <p>This indicates that the requested data could not be found.
 * Adjust parameters to be less strict as they may be too restrictive.</p>
 */
public class NotFoundException extends TwelveDataException {
    
    public NotFoundException(String message) {
        super(message, 404);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, 404, cause);
    }
} 