package com.example.numbersequenceprocessing.controller.rest;

import com.example.numbersequenceprocessing.data.dto.response.ApiResponse;
import com.example.numbersequenceprocessing.data.dto.request.FilePathRequest;
import com.example.numbersequenceprocessing.data.dto.request.NumberSequenceRequest;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import com.example.numbersequenceprocessing.service.NumberSequenceService;
import com.example.numbersequenceprocessing.utils.api.ResponseUtils;
import com.example.numbersequenceprocessing.utils.checksum.ChecksumUtils;
import com.example.numbersequenceprocessing.utils.file.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
@Api(description = "Searches for a file on the disk along the passed path and performs the requested operation on it")
public class NumberSequenceController {
    private final NumberSequenceService numberSequenceService;
    private final ResponseUtils responseUtils;
    private final FileUtils fileUtils;
    private final ChecksumUtils checksumUtils;

    public NumberSequenceController(NumberSequenceService numberSequenceService,
                                    ResponseUtils responseUtils,
                                    FileUtils fileUtils, ChecksumUtils checksumUtils) {
        this.numberSequenceService = numberSequenceService;
        this.responseUtils = responseUtils;
        this.fileUtils = fileUtils;
        this.checksumUtils = checksumUtils;
    }

    @ApiOperation("Executes the operation with the specified path for the file, passed as a request")
    @PostMapping("/perform-operation")
    public ResponseEntity<ApiResponse<Object>> performOperation(@RequestBody NumberSequenceRequest request)
            throws SequenceException, IOException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        Object data = numberSequenceService.performOperation(request.getOperation(), checksum, reader);
        return responseUtils.createResponse(data);
    }

    @ApiOperation("Finds the maximum number in the file")
    @PostMapping("/get-max-value")
    public ResponseEntity<ApiResponse<Integer>> getMaxValue(@RequestBody FilePathRequest request) throws IOException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        Integer maxValue = numberSequenceService.getMaxValue(checksum, reader);
        return responseUtils.createResponse(maxValue);
    }

    @ApiOperation("Finds the minimum number in the file")
    @PostMapping("/get-min-value")
    public ResponseEntity<ApiResponse<Integer>> getMinValue(@RequestBody FilePathRequest request) throws IOException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        Integer minValue = numberSequenceService.getMinValue(checksum, reader);
        return responseUtils.createResponse(minValue);
    }

    @ApiOperation("Finds the median of the numbers in the file")
    @PostMapping("/get-median")
    public ResponseEntity<ApiResponse<Double>> getMedian(@RequestBody FilePathRequest request) throws IOException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        Double median = numberSequenceService.getMedian(checksum, reader);
        return responseUtils.createResponse(median);
    }

    @ApiOperation("Finds the arithmetic average of the numbers in the file")
    @PostMapping("/get-mean")
    public ResponseEntity<ApiResponse<Double>> getMean(@RequestBody FilePathRequest request) throws IOException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        Double mean = numberSequenceService.getMean(checksum, reader);
        return responseUtils.createResponse(mean);
    }

    @ApiOperation("Finds the longest sequence of consecutive numbers that increases")
    @PostMapping("/get-increasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getIncreasingSequence(@RequestBody FilePathRequest request)
            throws IOException, SequenceException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfIncreasingNumbers(checksum, reader);
        return responseUtils.createResponse(sequence);
    }

    @ApiOperation("Finds the longest sequence of consecutive numbers that decreases")
    @PostMapping("/get-decreasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getDecreasingSequence(@RequestBody FilePathRequest request)
            throws IOException, SequenceException {
        InputStream reader = fileUtils.readFile(request.getFilePath());
        String checksum = checksumUtils.getChecksum(request.getFilePath());
        List<List<Integer>> sequence = numberSequenceService.getLongestSequenceOfDecreasingNumbers(checksum, reader);
        return responseUtils.createResponse(sequence);
    }
}
