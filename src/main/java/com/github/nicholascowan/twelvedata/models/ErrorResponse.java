package com.github.nicholascowan.twelvedata.models;

/**
 * Error response from the TwelveData API.
 */
public class ErrorResponse {
    private String status;
    private Integer code;
    private String message;
    
    // Default constructor
    public ErrorResponse() {}
    
    // Constructor with all fields
    public ErrorResponse(String status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
    // Getters and Setters
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    // Helper methods
    public boolean isError() {
        return "error".equals(status);
    }
    
    public boolean isOk() {
        return "ok".equals(status);
    }
    
    // Specific error type checks
    public boolean isBadRequest() {
        return code != null && code == 400;
    }
    
    public boolean isUnauthorized() {
        return code != null && code == 401;
    }
    
    public boolean isForbidden() {
        return code != null && code == 403;
    }
    
    public boolean isNotFound() {
        return code != null && code == 404;
    }
    
    public boolean isParameterTooLong() {
        return code != null && code == 414;
    }
    
    public boolean isRateLimited() {
        return code != null && code == 429;
    }
    
    public boolean isServerError() {
        return code != null && code >= 500;
    }
    
    public boolean isClientError() {
        return code != null && code >= 400 && code < 500;
    }
    
    /**
     * Get a human-readable description of the error based on the error code.
     * 
     * @return description of the error and suggested resolution
     */
    public String getErrorDescription() {
        if (code == null) {
            return "Unknown error occurred";
        }
        
        switch (code) {
            case 400:
                return "Bad Request: Invalid or incorrect parameter(s) provided. Check the message for details and refer to the API Documentation.";
            case 401:
                return "Unauthorized: Invalid or incorrect API key. Verify your API key is correct.";
            case 403:
                return "Forbidden: API key lacks permissions for the requested resource. An upgrade may be required.";
            case 404:
                return "Not Found: Requested data could not be found. Adjust parameters to be less strict.";
            case 414:
                return "Parameter Too Long: Input parameter array exceeds the allowed length. Follow the message guidance.";
            case 429:
                return "Too Many Requests: API request limit reached for your key. Wait briefly or upgrade your plan.";
            case 500:
                return "Internal Server Error: Server-side issue occurred. Retry later or contact support.";
            default:
                if (code >= 500) {
                    return "Server Error: Server-side issue occurred. Retry later or contact support.";
                } else if (code >= 400) {
                    return "Client Error: Invalid request. Check your parameters and API key.";
                } else {
                    return "Unknown error occurred";
                }
        }
    }
    
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
} 