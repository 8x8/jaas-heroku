package com.eght.token_generator.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ApiException extends RuntimeException {
    private final long throwTime = System.currentTimeMillis();
    private List<ErrorArgument> arguments;

    ApiException() {
    }

    ApiException(RuntimeException e) {
        super(e);
        this.arguments = Collections.emptyList();
    }

    ApiException(String message) {
        super(message);
        this.arguments = Collections.emptyList();
    }

    ApiException(String message, Throwable cause) {
        super(message, cause);
        this.arguments = Collections.emptyList();
    }

    ApiException(String message, List<ErrorArgument> arguments) {
        super(message);
        this.arguments = arguments;
    }

    ApiException(String message, Throwable cause, List<ErrorArgument> arguments) {
        super(message, cause);
        this.arguments = arguments;
    }

    public abstract HttpError getErrorCode();
}
