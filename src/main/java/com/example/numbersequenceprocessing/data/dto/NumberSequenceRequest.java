package com.example.numbersequenceprocessing.data.dto;

import com.example.numbersequenceprocessing.data.enums.OperationType;
import lombok.Data;

@Data
public class NumberSequenceRequest {
    private String filePath;
    private OperationType operation;
}
