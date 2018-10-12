package com.github.lukasgemela.fileprocessor.common.errorhandling.model;

public class ErrorMsg {

    private final String code;
    private final String message;

    public ErrorMsg(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}