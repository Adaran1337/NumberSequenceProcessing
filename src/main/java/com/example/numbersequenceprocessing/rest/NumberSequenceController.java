package com.example.numbersequenceprocessing.rest;

import com.example.numbersequenceprocessing.data.dto.ApiResponse;
import com.example.numbersequenceprocessing.data.dto.FilePathRequest;
import com.example.numbersequenceprocessing.data.dto.NumberSequenceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class NumberSequenceController {
    @PostMapping("/perform-operation")
    public <T> ResponseEntity<ApiResponse<T>>  performOperation(@RequestBody NumberSequenceRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PostMapping("/get-max-value")
    public ResponseEntity<ApiResponse<Integer>> getMaxValue(@RequestBody FilePathRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PostMapping("/get-min-value")
    public ResponseEntity<ApiResponse<Integer>> getMinValue(@RequestBody FilePathRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }

    @PostMapping("/get-median")
    public ResponseEntity<ApiResponse<Double>> getMedian(@RequestBody FilePathRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }
    @PostMapping("/get-mean")
    public ResponseEntity<ApiResponse<Double>> getMean(@RequestBody FilePathRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }
    @PostMapping("/get-increasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getIncreasingSequence(@RequestBody FilePathRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }
    @PostMapping("/get-decreasing-sequence")
    public ResponseEntity<ApiResponse<List<List<Integer>>>> getDecreasingSequence(@RequestBody FilePathRequest request) {
        return ResponseEntity.ok(new ApiResponse<>());
    }
}
