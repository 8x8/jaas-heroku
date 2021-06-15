package com.eght.token_generator.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements HttpError {
    GENERIC_INTERNAL(HttpStatus.INTERNAL_SERVER_ERROR, "internal.server.errors"),
    DEPENDENCY_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "dependency.request.failed"),
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "validation.failed");

    private final HttpStatus httpStatus;
    private final String errorKey;

    ErrorCode(HttpStatus httpStatus, String errorKey) {
        this.httpStatus = httpStatus;
        this.errorKey = errorKey;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getErrorKey() {
        return this.errorKey;
    }
}

