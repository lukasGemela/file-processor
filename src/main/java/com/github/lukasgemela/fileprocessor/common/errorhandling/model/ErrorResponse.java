package com.github.lukasgemela.fileprocessor.common.errorhandling.model;

import java.util.List;

public class ErrorResponse {

    private final List<ErrorMsg> errors;

    public ErrorResponse(List<ErrorMsg> errors) {
        this.errors = errors;
    }

    public List<ErrorMsg> getErrors() {
        return errors;
    }
}
