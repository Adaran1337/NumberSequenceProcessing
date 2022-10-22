package com.example.numbersequenceprocessing.service;

import com.example.numbersequenceprocessing.data.enums.OperationType;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class NumberSequenceService {
    /**
     * Finds the maximum number in the file
     * @param filePath absolute path to file
     * @return maximum number
     * @throws IOException if something goes wrong while reading a file
     */
    public Integer getMaxValue(String filePath) throws IOException {
        IntStream reader = readFile(filePath);
        return reader.max().orElseThrow();
    }

    /**
     * Finds the minimum number in the file
     * @param filePath absolute path to file
     * @return minimum  number
     * @throws IOException if something goes wrong while reading a file
     */
    public Integer getMinValue(String filePath) throws IOException {
        IntStream reader = readFile(filePath);
        return reader.min().orElseThrow();
    }

    /**
     * Finds the median of the numbers in the specified file
     * @param filePath absolute path to file
     * @return median of the numbers
     * @throws IOException if something goes wrong while reading a file
     */
    public Double getMedian(String filePath) throws IOException {
        List<Double> lines = Files.lines(new File(filePath).toPath())
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
     * @param filePath absolute path to file
     * @return mean of the numbers
     * @throws IOException if something goes wrong while reading a file
     */
    public Double getMean(String filePath) throws IOException {
        IntStream reader = readFile(filePath);
        return reader.average().orElseThrow();
    }

    /**
     * Finds the longest sequence of consecutive numbers, which increases
     * @param filePath absolute path to file
     * @return sequences of increasing numbers
     * @throws IOException if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    public List<List<Integer>> getLongestSequenceOfIncreasingNumbers(String filePath) throws IOException, SequenceException {
        return getLongestSequence(filePath, OperationType.INCREASING_SEQUENCE);
    }

    /**
     * Finds the longest sequence of consecutive numbers, which decreases
     * @param filePath absolute path to file
     * @return sequences of decreasing numbers
     * @throws IOException if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    public List<List<Integer>> getLongestSequenceOfDecreasingNumbers(String filePath) throws IOException, SequenceException {
        return getLongestSequence(filePath, OperationType.DECREASING_SEQUENCE);
    }

    private List<List<Integer>> getLongestSequence(String filePath, OperationType operation) throws IOException, SequenceException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
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

    private IntStream readFile(String filePath) throws IOException {
        return Files.lines(new File(filePath).toPath()).mapToInt(Integer::parseInt);
    }
}
