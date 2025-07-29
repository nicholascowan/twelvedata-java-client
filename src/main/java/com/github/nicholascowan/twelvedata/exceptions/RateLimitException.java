package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 429 Too Many Requests error.
 * 
 * <p>This indicates that the API request limit has been reached for your key.
 * Wait briefly or upgrade your plan to resolve this issue.</p>
 */
public class RateLimitException extends TwelveDataException {
    
    public RateLimitException(String message) {
        super(message, 429);
    }
    
    public RateLimitException(String message, Throwable cause) {
        super(message, 429, cause);
    }
} 