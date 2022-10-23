package com.example.numbersequenceprocessing.data.dto.request;

import com.example.numbersequenceprocessing.data.enums.OperationType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Request data model containing the path " +
        "to the file on which you want to perform the passed operation")
public class NumberSequenceRequest {

    @ApiModelProperty(value = "Absolute path to the file located on the server", example = "C:/test_data/file.txt")
    private String filePath;

    @ApiModelProperty(value = "The type of operation to be performed", example = "MAX_VALUE")
    private OperationType operation;
}
