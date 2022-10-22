package com.example.numbersequenceprocessing.utils.api;

import com.example.numbersequenceprocessing.data.dto.response.ApiErrorResponse;
import com.example.numbersequenceprocessing.data.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {
    /**
     * Creates response entity with passed data
     * @param data any element or collection
     * @return response entity with 200 status
     * @param <T> any type of class
     */
    public <T> ResponseEntity<ApiResponse<T>> createResponse(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage("successful request");
        response.setStatus(HttpStatus.OK);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    /**
     * Creates error response with passed arguments
     * @param th error that occurred
     * @param message message for user
     * @param status status of the returned http response
     * @return new {@link ResponseEntity} with filled fields in the {@link ApiErrorResponse}
     */
    public ResponseEntity<ApiErrorResponse> createErrorResponse(Throwable th, String message, HttpStatus status) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(status.name());
        response.setMessage(message);
        response.setDebugMessage(th.getMessage());

        return new ResponseEntity<>(response, status);
    }
}

