package com.example.numbersequenceprocessing.service;

import com.example.numbersequenceprocessing.data.enums.OperationType;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class NumberSequenceService {

    /**
     * Performs the operation depending on the {@link OperationType} provided and returns the result.
     * @param operation type of operation to perform
     * @param reader file data
     * @return result of the selected operation
     * @throws IOException if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    public Object performOperation(OperationType operation, BufferedReader reader)
            throws IOException, SequenceException {
        switch (operation){
            case MAX_VALUE: return getMaxValue(reader);
            case MIN_VALUE: return getMinValue(reader);
            case MEDIAN: return getMedian(reader);
            case MEAN: return getMean(reader);
            case INCREASING_SEQUENCE:
            case DECREASING_SEQUENCE:
                return getLongestSequence(reader, operation);
            default: throw new IllegalStateException("Provided unsupported operation");
        }
    }

    /**
     * Finds the maximum number in the file
     * @param reader file data
     * @return maximum number
     */
    public Integer getMaxValue(BufferedReader reader) {
        IntStream numbers = readFile(reader);
        return numbers.max().orElseThrow();
    }

    /**
     * Finds the minimum number in the file
     * @param reader file data
     * @return minimum  number
     */
    public Integer getMinValue(BufferedReader reader) {
        IntStream numbers = readFile(reader);
        return numbers.min().orElseThrow();
    }

    /**
     * Finds the median of the numbers in the specified file
     * @param reader file data
     * @return median of the numbers
     */
    public Double getMedian(BufferedReader reader) {
        List<Double> lines = reader.lines()
                .map(Double::parseDouble)
                .sorted()
                .collect(Collectors.toList());

        int size = lines.size();

        return size % 2 == 1?
                lines.get(size / 2) :
                (lines.get(size / 2 - 1) + lines.get(size / 2)) / 2;
    }

    /**
     * Finds the mean of the numbers in the specified file
     * @param reader file data
     * @return mean of the numbers
     */
    public Double getMean(BufferedReader reader) {
        IntStream numbers = readFile(reader);
        return numbers.average().orElseThrow();
    }

    /**
     * Finds the longest sequence of consecutive numbers, which increases
     * @param reader file data
     * @return sequences of increasing numbers
     * @throws IOException if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    public List<List<Integer>> getLongestSequenceOfIncreasingNumbers(BufferedReader reader) throws IOException, SequenceException {
        return getLongestSequence(reader, OperationType.INCREASING_SEQUENCE);
    }

    /**
     * Finds the longest sequence of consecutive numbers, which decreases
     * @param reader file data
     * @return sequences of decreasing numbers
     * @throws IOException if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    public List<List<Integer>> getLongestSequenceOfDecreasingNumbers(BufferedReader reader) throws IOException, SequenceException {
        return getLongestSequence(reader, OperationType.DECREASING_SEQUENCE);
    }

    private List<List<Integer>> getLongestSequence(BufferedReader reader, OperationType operation)
            throws IOException, SequenceException {
        List<List<Integer>> sequence = new ArrayList<>();

        List<Integer> subSequence = new ArrayList<>();
        Integer previousNumber = operation == OperationType.DECREASING_SEQUENCE ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Comparator<Integer> comparator = operation == OperationType.INCREASING_SEQUENCE ?
                Comparator.reverseOrder() : Integer::compareTo;

        while (reader.ready()) {
            Integer currentNumber = Integer.parseInt(reader.readLine());
            if (comparator.compare(previousNumber, currentNumber) > 0) {
                if (subSequence.size() == 0) {
                    subSequence.add(previousNumber);
                }
                subSequence.add(currentNumber);
            }  else if (subSequence.size() != 0) {
                sequence.add(subSequence);
                subSequence = new ArrayList<>();
            }

            previousNumber = currentNumber;
        }

        if (subSequence.size() != 0) {
            sequence.add(subSequence);
        }

        long longestSequenceSize = sequence.stream().mapToInt(List::size).max().orElseThrow(SequenceException::new);
        sequence = sequence.stream().filter(s -> s.size() == longestSequenceSize).collect(Collectors.toList());

        return sequence;
    }

    private IntStream readFile(BufferedReader reader) {
        return reader.lines().mapToInt(Integer::parseInt);
    }
}
