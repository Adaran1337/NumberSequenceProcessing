package com.example.numbersequenceprocessing.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NumberSequenceService {

    public Integer getMaxValue() {
        return 0;
    }

    public Integer getMinValue() {
        return 0;
    }

    public Double getMedian() {
        return 0.0;
    }

    public Double getMean() {
        return 0.0;
    }

    public List<List<Integer>> getLongestSequenceOfIncreasingNumbers() {
        return new ArrayList<>();
    }

    public List<List<Integer>> getLongestSequenceOfDecreasingNumbers() {
        return new ArrayList<>();
    }
}
