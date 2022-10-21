package com.example.numbersequenceprocessing.data.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
