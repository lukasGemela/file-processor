package com.github.lukasgemela.fileprocessor.common.errorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

public class ServiceException extends RuntimeException {

    private static final Pattern CODE_PATTERN = Pattern.compile("[a-z0-9][a-z0-9.]*[a-z0-9]");

    private final String code;
    private final HttpStatus httpStatus;

    public ServiceException(HttpStatus httpStatus, String code, String message) {
        super(message);
        Assert.notNull(httpStatus, "'httpStatus' must not be null");
        Assert.hasLength(code, "'code' must not be empty");
        Assert.hasLength(message, "'message' must not be empty");
        Assert.isTrue(CODE_PATTERN.matcher(code).matches(), "'code' must match "+CODE_PATTERN);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "httpStatus=" + httpStatus +
                ", code='" + code + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
