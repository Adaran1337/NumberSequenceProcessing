package com.example.numbersequenceprocessing.data.dto.response;

import lombok.Data;

@Data
public class ApiErrorResponse {
    private String message;
    private String debugMessage;
    private String status;
}
