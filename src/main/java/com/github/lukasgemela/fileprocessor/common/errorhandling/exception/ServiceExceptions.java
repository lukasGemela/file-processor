package com.github.lukasgemela.fileprocessor.common.errorhandling.exception;

import org.springframework.http.HttpStatus;

public class ServiceExceptions {
    public static ServiceException badRequest(String code, String message) {
        return new ServiceException(HttpStatus.BAD_REQUEST, code, message);
    }

    public static ServiceException internalServerError(String code, String message) {
        return new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }
}
