package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API rate limit is exceeded.
 */
public class RateLimitException extends TwelveDataException {
    
    public RateLimitException(String message) {
        super(message, 429);
    }
    
    public RateLimitException(String message, Throwable cause) {
        super(message, 429, cause);
    }
} 