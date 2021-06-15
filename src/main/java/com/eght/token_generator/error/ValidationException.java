package com.eght.token_generator.error;

import java.util.List;

public class ValidationException extends ApiException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(String message, List<ErrorArgument> arguments) {
        super(message, arguments);
    }

    public ValidationException(String message, Throwable cause, List<ErrorArgument> arguments) {
        super(message, cause, arguments);
    }

    public ValidationException(RuntimeException e) {
        super(e);
    }

    public HttpError getErrorCode() {
        return ErrorCode.VALIDATION_EXCEPTION;
    }
}

