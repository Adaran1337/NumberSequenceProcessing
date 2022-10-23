package com.example.numbersequenceprocessing.controller.advice;

import com.example.numbersequenceprocessing.data.dto.response.ApiErrorResponse;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import com.example.numbersequenceprocessing.utils.api.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandlerControllerAdvice {
    private final ResponseUtils responseUtils;

    public GlobalExceptionHandlerControllerAdvice(ResponseUtils responseUtils) {
        this.responseUtils = responseUtils;
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleFileNotFoundException(Exception ex) {
        return responseUtils.createErrorResponse(
                ex,
                "No such file was found in the passed path",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiErrorResponse> handleIOException(Exception ex) {
        return responseUtils.createErrorResponse(
                ex,
                "Something went wrong during file processing",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SequenceException.class)
    public ResponseEntity<ApiErrorResponse> handleSequenceException(Exception ex) {
        return responseUtils.createErrorResponse(
                ex,
                "No sequences were found in the file",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponse> handleNoSuchElementException(Exception ex) {
        return responseUtils.createErrorResponse(
                ex,
                "Provided file is empty",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ApiErrorResponse> handleNumberFormatException(Exception ex) {
        return responseUtils.createErrorResponse(
                ex,
                "Invalid characters in the file provided",
                HttpStatus.BAD_REQUEST);
    }
}
