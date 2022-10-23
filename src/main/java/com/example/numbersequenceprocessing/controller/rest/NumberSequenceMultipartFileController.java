package com.example.numbersequenceprocessing.controller.rest;

import com.example.numbersequenceprocessing.data.dto.response.ApiResponse;
import com.example.numbersequenceprocessing.data.enums.OperationType;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import com.example.numbersequenceprocessing.service.NumberSequenceService;
import com.example.numbersequenceprocessing.utils.api.ResponseUtils;
import com.example.numbersequenceprocessing.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/multipart-file/")
@Api(description = "Performs the requested operation on the transmitted file")
public class NumberSequenceMultipartFileController {

    private final NumberSequenceService numberSequenceService;
    private final ResponseUtils responseUtils;
    private final FileUtils fileUtils;

    public NumberSequenceMultipartFileController(NumberSequenceService numberSequenceService,
                                    ResponseUtils responseUtils,
                                    FileUtils fileUtils) {
        this.numberSequenceService = numberSequenceService;
        this.responseUtils = responseUtils;
        this.fileUtils = fileUtils;
    }

    @ApiOperation(value = "Executes the operation with the specified path for the file, passed as a request")
    @PostMapping(path = "/perform-operation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> performOperation(
            @RequestParam("textFile") MultipartFile file,
            @RequestParam("operation") OperationType operation)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(file);
        Object data = numberSequenceService.performOperation(operation, reader);
        return responseUtils.createResponse(data);
    }

    @ApiOperation("Finds the maximum number in the file")
    @PostMapping(path = "/get-max-value", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Integer>> getMaxValue(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Integer maxValue = numberSequenceService.getMaxValue(reader);
        return responseUtils.createResponse(maxValue);
    }

    @ApiOperation("Finds the minimum number in the file")
    @PostMapping(path = "/get-min-value", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Integer>> getMinValue(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Integer minValue = numberSequenceService.getMinValue(reader);
        return responseUtils.createResponse(minValue);
    }

    @ApiOperation("Finds the median of the numbers in the file")
    @PostMapping(path = "/get-median", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Double>> getMedian(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Double median = numberSequenceService.getMedian(reader);
        return responseUtils.createResponse(median);
    }

    @ApiOperation("Finds the arithmetic average of the numbers in the file")
    @PostMapping(path = "/get-mean", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Double>> getMean(@RequestParam("textFile") MultipartFile file)
            throws IOException {
        BufferedReader reader = fileUtils.readFile(file);
        Double mean = numberSequenceService.getMean(reader);
        return responseUtils.createResponse(mean);
    }

    @ApiOperation("Finds the longest sequence of consecutive numbers that increases")
    @PostMapping(path = "/get-increasing-sequence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getIncreasingSequence(
            @RequestParam("textFile") MultipartFile file)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(file);
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfIncreasingNumbers(reader);
        return responseUtils.createResponse(sequence);
    }

    @ApiOperation("Finds the longest sequence of consecutive numbers that decreases")
    @PostMapping(path = "/get-decreasing-sequence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getDecreasingSequence(
            @RequestParam("textFile") MultipartFile file)
            throws IOException, SequenceException {
        BufferedReader reader = fileUtils.readFile(file);
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfDecreasingNumbers(reader);
        return responseUtils.createResponse(sequence);
    }
}
