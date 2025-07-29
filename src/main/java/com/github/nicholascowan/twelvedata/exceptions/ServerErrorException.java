package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Exception thrown when the API returns a server error (5xx).
 */
public class ServerErrorException extends TwelveDataException {
    
    public ServerErrorException(String message, int errorCode) {
        super(message, errorCode);
    }
    
    public ServerErrorException(String message, int errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
} 