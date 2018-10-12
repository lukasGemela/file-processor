package com.github.lukasgemela.fileprocessor.common.errorhandling.model;

import com.github.lukasgemela.fileprocessor.common.errorhandling.exception.ServiceException;

import static java.util.Collections.singletonList;

public class ErrorResponseFactory {

    private ErrorResponseFactory() {}

    public static ErrorResponse errorResponseFrom(ServiceException e) {
        return new ErrorResponse(singletonList(errorMsgFrom(e)));
    }

    public static ErrorMsg errorMsgFrom(ServiceException e) {
        return new ErrorMsg(e.getCode(), e.getMessage());
    }

    public static ErrorResponse errorResponseFrom(String code, String message) {
        return new ErrorResponse(singletonList(new ErrorMsg(code, message)));
    }
}

