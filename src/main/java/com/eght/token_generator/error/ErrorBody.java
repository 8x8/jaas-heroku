package com.eght.token_generator.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class ErrorBody implements Serializable {
    private static final String EMPTY = "";
    private long timestamp;
    private long status;
    private String message;
    private String messageKey;
    private String path;
    private List<ErrorArgument> arguments;

    static ErrorBody create(HttpServletRequest request, long timestamp, String message, List<ErrorArgument> arguments, HttpError code) {
        ErrorBody body = new ErrorBody();
        body.setMessage(message);
        body.setArguments(arguments);
        body.setMessageKey(code.getErrorKey());
        body.setTimestamp(timestamp);
        body.setPath(getPath(request));
        body.setStatus(code.getHttpStatus().value());
        return body;
    }

    private static String getPath(HttpServletRequest request) {
        String requestURI = request.getRequestURI() == null ? "" : request.getRequestURI();
        String contextPath = request.getContextPath() == null ? "" : request.getContextPath();
        return requestURI.startsWith(contextPath) ? requestURI.substring(contextPath.length()) : "";
    }
}
