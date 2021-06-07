package com.example.togepi.enums;

public enum Error {

    GENERAL_ERROR(500, "General error", "0000"),
    TIMEOUT_ERROR(408, "Request timeout", "0001"),
    INVALID_PARAM(400, "Invalid param", "0002"),
    URL_NOT_FOUND(404, "URL not found", "0003"),
    METHOD_NOT_ALLOWED(405, "Method not allowed", "0004"),
    
    RESOURCE_NOT_FOUND(404, "Resource not found", "1000");

    private final Integer statusCode;
    private final String message;
    private final String errorCode;

    Error(Integer statusCode, String message, String errorCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
