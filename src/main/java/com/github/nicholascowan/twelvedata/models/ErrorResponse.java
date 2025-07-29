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
    
    public boolean isRateLimited() {
        return code != null && code == 429;
    }
    
    public boolean isServerError() {
        return code != null && code >= 500;
    }
    
    public boolean isClientError() {
        return code != null && code >= 400 && code < 500;
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