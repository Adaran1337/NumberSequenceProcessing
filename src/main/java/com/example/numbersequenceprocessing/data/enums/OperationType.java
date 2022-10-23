package com.example.numbersequenceprocessing.data.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Available operations to perform")
public enum OperationType {
    MAX_VALUE,
    MIN_VALUE,
    MEDIAN,
    MEAN,
    INCREASING_SEQUENCE,
    DECREASING_SEQUENCE
}
