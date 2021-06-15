package com.eght.token_generator.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DependencyException extends ApiException {
    public DependencyException(String message) {
        super(message);
    }

    public HttpError getErrorCode() {
        return ErrorCode.DEPENDENCY_EXCEPTION;
    }
}
