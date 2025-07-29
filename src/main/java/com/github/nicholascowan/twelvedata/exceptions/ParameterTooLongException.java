package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a 414 Parameter Too Long error.
 * 
 * <p>This indicates that an input parameter array exceeds the allowed length.
 * Follow the message guidance to adjust the parameter length.</p>
 */
public class ParameterTooLongException extends TwelveDataException {
    
    public ParameterTooLongException(String message) {
        super(message, 414);
    }
    
    public ParameterTooLongException(String message, Throwable cause) {
        super(message, 414, cause);
    }
} 