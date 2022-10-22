package com.example.numbersequenceprocessing.rest;

import com.example.numbersequenceprocessing.data.dto.response.ApiErrorResponse;
import com.example.numbersequenceprocessing.data.dto.response.ApiResponse;
import com.example.numbersequenceprocessing.data.dto.request.FilePathRequest;
import com.example.numbersequenceprocessing.data.dto.request.NumberSequenceRequest;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import com.example.numbersequenceprocessing.service.NumberSequenceService;
import com.example.numbersequenceprocessing.utils.api.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequestMapping("/api")
public class NumberSequenceController {
    private final NumberSequenceService numberSequenceService;
    private final ResponseUtils responseUtils;

    public NumberSequenceController(NumberSequenceService numberSequenceService, ResponseUtils responseUtils) {
        this.numberSequenceService = numberSequenceService;
        this.responseUtils = responseUtils;
    }

    @PostMapping("/perform-operation")
    public ResponseEntity<ApiResponse<Object>>  performOperation(@RequestBody NumberSequenceRequest request) throws SequenceException, IOException {
        Object data = numberSequenceService.performOperation(request.getOperation(), request.getFilePath());
        return responseUtils.createResponse(data);
    }

    @PostMapping("/get-max-value")
    public ResponseEntity<ApiResponse<Integer>> getMaxValue(@RequestBody FilePathRequest request) throws IOException {
        Integer maxValue = numberSequenceService.getMaxValue(request.getFilePath());
        return responseUtils.createResponse(maxValue);
    }

    @PostMapping("/get-min-value")
    public ResponseEntity<ApiResponse<Integer>> getMinValue(@RequestBody FilePathRequest request) throws IOException {
        String filePath = request.getFilePath();
        Integer minValue = numberSequenceService.getMinValue(filePath);
        return responseUtils.createResponse(minValue);
    }

    @PostMapping("/get-median")
    public ResponseEntity<ApiResponse<Double>> getMedian(@RequestBody FilePathRequest request) throws IOException {
        String filePath = request.getFilePath();
        Double median = numberSequenceService.getMedian(filePath);
        return responseUtils.createResponse(median);
    }

    @PostMapping("/get-mean")
    public ResponseEntity<ApiResponse<Double>> getMean(@RequestBody FilePathRequest request) throws IOException {
        String filePath = request.getFilePath();
        Double mean = numberSequenceService.getMean(filePath);
        return responseUtils.createResponse(mean);
    }

    @PostMapping("/get-increasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getIncreasingSequence(@RequestBody FilePathRequest request)
            throws IOException, SequenceException {
        String filePath = request.getFilePath();
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfIncreasingNumbers(filePath);
        return responseUtils.createResponse(sequence);
    }

    @PostMapping("/get-decreasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getDecreasingSequence(@RequestBody FilePathRequest request)
            throws IOException, SequenceException {
        String filePath = request.getFilePath();
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfDecreasingNumbers(filePath);
        return responseUtils.createResponse(sequence);
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
