package com.eght.token_generator.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.eght.token_generator.error.ErrorCode.GENERIC_INTERNAL;
import static java.lang.System.currentTimeMillis;
import static java.util.Collections.emptyList;

@ControllerAdvice
@Slf4j
public class ErrorManager {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorBody> handleMethodArgumentNotValidException(HttpServletRequest httpRequest, MethodArgumentNotValidException e) {
        List args = e.getBindingResult().getAllErrors().stream().map((fe) -> fe instanceof FieldError ? new ErrorArgument(((FieldError) fe).getField(), fe.getDefaultMessage()) : new ErrorArgument("undefinedField", fe.getDefaultMessage())).collect(Collectors.toList());
        ErrorBody eb = ErrorBody.create(httpRequest, System.currentTimeMillis(), "javax validation failed", args, ErrorCode.VALIDATION_EXCEPTION);
        log.error("exception {} body  {}", e.getClass(), eb);
        return ResponseEntity.status(ErrorCode.VALIDATION_EXCEPTION.getHttpStatus()).body(eb);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorBody> handleApiException(HttpServletRequest httpRequest, ApiException ex) {
        ErrorBody eb = ErrorBody.create(httpRequest, ex.getThrowTime(), ex.getMessage(), ex.getArguments(), ex.getErrorCode());
        log.error("exception {} body {}", ex.getClass(), eb);
        return ResponseEntity.status(ex.getErrorCode().getHttpStatus()).body(eb);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorBody> handleExceptions(HttpServletRequest request, Exception ex) {
        log.error("", ex);
        ErrorBody eb = ErrorBody.create(request, currentTimeMillis(), ex.getMessage(), emptyList(), GENERIC_INTERNAL);
        log.error("generic internal {}", eb);
        return ResponseEntity.status(GENERIC_INTERNAL.getHttpStatus()).body(eb);
    }
}
