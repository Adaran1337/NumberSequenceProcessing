package com.example.numbersequenceprocessing.data.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@ApiModel(description = "Response data model, containing basic information about the error that occurred")
public class ApiErrorResponse {

    @ApiModelProperty(value = "Error message from the server")
    private String message;

    @ApiModelProperty(value = "Detailed description of the error that occurred")
    private String debugMessage;

    @ApiModelProperty(value = "Server response status")
    private HttpStatus status;
}
