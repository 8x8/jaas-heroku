package com.eght.token_generator.error;

import org.springframework.http.HttpStatus;

public interface HttpError {
    HttpStatus getHttpStatus();
    String getErrorKey();
}
