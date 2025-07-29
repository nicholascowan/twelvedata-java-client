package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 403 Forbidden error.
 * 
 * <p>This indicates that the API key lacks permissions for the requested resource.
 * An upgrade to your plan may be required.</p>
 */
public class ForbiddenException extends TwelveDataException {
    
    public ForbiddenException(String message) {
        super(message, 403);
    }
    
    public ForbiddenException(String message, Throwable cause) {
        super(message, 403, cause);
    }
} 