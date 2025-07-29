package com.github.nicholascowan.twelvedata.exceptions;

/**
 * Base exception for all TwelveData API errors.
 */
public class TwelveDataException extends RuntimeException {
    
    private final int errorCode;
    
    public TwelveDataException(String message) {
        super(message);
        this.errorCode = 0;
    }
    
    public TwelveDataException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public TwelveDataException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 0;
    }
    
    public TwelveDataException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
} 