package com.example.numbersequenceprocessing.rest;

import com.example.numbersequenceprocessing.data.dto.response.ApiErrorResponse;
import com.example.numbersequenceprocessing.data.dto.response.ApiResponse;
import com.example.numbersequenceprocessing.data.dto.request.FilePathRequest;
import com.example.numbersequenceprocessing.data.dto.request.NumberSequenceRequest;
import com.example.numbersequenceprocessing.data.enums.OperationType;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import com.example.numbersequenceprocessing.service.NumberSequenceService;
import com.example.numbersequenceprocessing.utils.api.ResponseUtils;
import com.example.numbersequenceprocessing.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequestMapping("/api")
public class NumberSequenceController {
    private final NumberSequenceService numberSequenceService;
    private final ResponseUtils responseUtils;
    private final FileUtils fileUtils;

    public NumberSequenceController(NumberSequenceService numberSequenceService,
                                    ResponseUtils responseUtils,
                                    FileUtils fileUtils) {
        this.numberSequenceService = numberSequenceService;
        this.responseUtils = responseUtils;
        this.fileUtils = fileUtils;
    }

    @PostMapping("/perform-operation")
    public ResponseEntity<ApiResponse<Object>> performOperation(@RequestBody NumberSequenceRequest request)
            throws SequenceException, IOException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        Object data = numberSequenceService.performOperation(request.getOperation(), reader);
        return responseUtils.createResponse(data);
    }

    @PostMapping(path = "/perform-operation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> performOperation(
            @RequestParam("textFile") MultipartFile file,
            @RequestParam("operation") OperationType operation)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(file);
        Object data = numberSequenceService.performOperation(operation, reader);
        return responseUtils.createResponse(data);
    }

    @PostMapping("/get-max-value")
    public ResponseEntity<ApiResponse<Integer>> getMaxValue(@RequestBody FilePathRequest request) throws IOException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        Integer maxValue = numberSequenceService.getMaxValue(reader);
        return responseUtils.createResponse(maxValue);
    }

    @PostMapping(path = "/get-max-value", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Integer>> getMaxValue(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Integer maxValue = numberSequenceService.getMaxValue(reader);
        return responseUtils.createResponse(maxValue);
    }

    @PostMapping("/get-min-value")
    public ResponseEntity<ApiResponse<Integer>> getMinValue(@RequestBody FilePathRequest request) throws IOException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        Integer minValue = numberSequenceService.getMinValue(reader);
        return responseUtils.createResponse(minValue);
    }

    @PostMapping(path = "/get-min-value", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Integer>> getMinValue(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Integer minValue = numberSequenceService.getMinValue(reader);
        return responseUtils.createResponse(minValue);
    }

    @PostMapping("/get-median")
    public ResponseEntity<ApiResponse<Double>> getMedian(@RequestBody FilePathRequest request) throws IOException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        Double median = numberSequenceService.getMedian(reader);
        return responseUtils.createResponse(median);
    }

    @PostMapping(path = "/get-median", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Double>> getMedian(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Double median = numberSequenceService.getMedian(reader);
        return responseUtils.createResponse(median);
    }

    @PostMapping("/get-mean")
    public ResponseEntity<ApiResponse<Double>> getMean(@RequestBody FilePathRequest request) throws IOException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        Double mean = numberSequenceService.getMean(reader);
        return responseUtils.createResponse(mean);
    }

    @PostMapping(path = "/get-mean", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Double>> getMean(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Double mean = numberSequenceService.getMean(reader);
        return responseUtils.createResponse(mean);
    }

    @PostMapping("/get-increasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getIncreasingSequence(@RequestBody FilePathRequest request)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfIncreasingNumbers(reader);
        return responseUtils.createResponse(sequence);
    }

    @PostMapping(path = "/get-increasing-sequence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getIncreasingSequence(
            @RequestParam("textFile") MultipartFile file)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(file);
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfIncreasingNumbers(reader);
        return responseUtils.createResponse(sequence);
    }

    @PostMapping("/get-decreasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getDecreasingSequence(@RequestBody FilePathRequest request)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(request.getFilePath());
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfDecreasingNumbers(reader);
        return responseUtils.createResponse(sequence);
    }

    @PostMapping(path = "/get-decreasing-sequence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getDecreasingSequence(
            @RequestParam("textFile") MultipartFile file)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(file);
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfDecreasingNumbers(reader);
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
