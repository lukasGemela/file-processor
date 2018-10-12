package com.github.lukasgemela.fileprocessor.common.errorhandling;

import com.github.lukasgemela.fileprocessor.common.errorhandling.exception.ServiceException;
import com.github.lukasgemela.fileprocessor.common.errorhandling.model.Constants;
import com.github.lukasgemela.fileprocessor.common.errorhandling.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.github.lukasgemela.fileprocessor.common.errorhandling.model.ErrorResponseFactory.errorResponseFrom;

@ControllerAdvice
public class CommonControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(CommonControllerAdvice.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(errorResponseFrom(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponseFrom(
                        Constants.CODE_BAD_REQUEST,
                        "Request body was empty or not in the required format"
                ));
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorResponse> handleServletRequestBindingException(ServletRequestBindingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponseFrom(
                        Constants.CODE_BAD_REQUEST,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleJavaLangException(Exception e) {
        logger.error("Unexpected exception: {}", e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponseFrom(
                        Constants.CODE_TECHNICAL_FAILURE,
                        e.toString()
                ));
    }
}