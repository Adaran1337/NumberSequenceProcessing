package com.example.numbersequenceprocessing.data.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@ApiModel(description = "Response data model, on successful execution of the query")
public class ApiResponse<T> {

    @ApiModelProperty(value = "Server response status")
    private HttpStatus status;

    @ApiModelProperty(value = "Error message from the server")
    private String message;

    @ApiModelProperty(value = "Data of executed query, form of the data depends on the type of operation performed")
    private T data;
}
