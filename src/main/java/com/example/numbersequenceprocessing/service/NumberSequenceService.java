package com.example.numbersequenceprocessing.service;

import com.example.numbersequenceprocessing.cache.ChecksumKeyGenerator;
import com.example.numbersequenceprocessing.data.enums.OperationType;
import com.example.numbersequenceprocessing.data.exception.SequenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class NumberSequenceService {

    private final NumberSequenceService self;

    private final ChecksumKeyGenerator checksumKeyGenerator;

    public NumberSequenceService(NumberSequenceService self, ChecksumKeyGenerator checksumKeyGenerator) {
        this.self = self;
        this.checksumKeyGenerator = checksumKeyGenerator;
    }

    /**
     * Performs the operation depending on the {@link OperationType} provided and returns the result.
     *
     * @param operation type of operation to perform
     * @param reader    file data
     * @param checksum  used as a component of the cache key
     * @return result of the selected operation
     * @throws IOException       if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    @Cacheable(value = "Numbers")
    public Object performOperation(OperationType operation, String checksum, InputStream reader)
            throws IOException, SequenceException {
        switch (operation) {
            case MAX_VALUE:
                return self.getMaxValue(checksum, reader);
            case MIN_VALUE:
                return self.getMinValue(checksum, reader);
            case MEDIAN:
                return self.getMedian(checksum, reader);
            case MEAN:
                return self.getMean(checksum, reader);
            case INCREASING_SEQUENCE:
                return self.getLongestSequenceOfIncreasingNumbers(checksum, reader);
            case DECREASING_SEQUENCE:
                return self.getLongestSequenceOfDecreasingNumbers(checksum, reader);
            default:
                throw new IllegalStateException("Provided unsupported operation");
        }
    }

    /**
     * Finds the maximum number in the file
     *
     * @param checksum  used as a component of the cache key
     * @param reader file data
     * @return maximum number
     */
    @Cacheable(value = "Numbers", keyGenerator = "checksumKeyGenerator")
    public Integer getMaxValue(String checksum, InputStream reader) {
        IntStream numbers = readFile(reader);
        return numbers.max().orElseThrow();
    }

    /**
     * Finds the minimum number in the file
     *
     * @param checksum  used as a component of the cache key
     * @param reader file data
     * @return minimum  number
     */
    @Cacheable(value = "Numbers", keyGenerator = "checksumKeyGenerator")
    public Integer getMinValue(String checksum, InputStream reader) {
        IntStream numbers = readFile(reader);
        return numbers.min().orElseThrow();
    }

    /**
     * Finds the median of the numbers in the specified file
     *
     * @param checksum  used as a component of the cache key
     * @param reader file data
     * @return median of the numbers
     */
    @Cacheable(value = "Numbers", keyGenerator = "checksumKeyGenerator")
    public Double getMedian(String checksum, InputStream reader) {
        List<Double> lines = (new BufferedReader(new InputStreamReader(reader))).lines()
                .map(Double::parseDouble)
                .sorted()
                .collect(Collectors.toList());

        int size = lines.size();

        if (lines.isEmpty()) {
            throw new NoSuchElementException("File is empty");
        }

        return size % 2 == 1 ?
                lines.get(size / 2) :
                (lines.get(size / 2 - 1) + lines.get(size / 2)) / 2;
    }

    /**
     * Finds the mean of the numbers in the specified file
     *
     * @param checksum  used as a component of the cache key
     * @param reader file data
     * @return mean of the numbers
     */
    @Cacheable(value = "Numbers", keyGenerator = "checksumKeyGenerator")
    public Double getMean(String checksum, InputStream reader) {
        IntStream numbers = readFile(reader);
        return numbers.average().orElseThrow();
    }

    /**
     * Finds the longest sequence of consecutive numbers, which increases
     *
     * @param checksum  used as a component of the cache key
     * @param reader file data
     * @return sequences of increasing numbers
     * @throws IOException       if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    @Cacheable(value = "Numbers", keyGenerator = "checksumKeyGenerator")
    public List<List<Integer>> getLongestSequenceOfIncreasingNumbers(String checksum, InputStream reader)
            throws IOException, SequenceException {
        return getLongestSequence(reader, OperationType.INCREASING_SEQUENCE);
    }

    /**
     * Finds the longest sequence of consecutive numbers, which decreases
     *
     * @param checksum  used as a component of the cache key
     * @param reader file data
     * @return sequences of decreasing numbers
     * @throws IOException       if something goes wrong while reading a file
     * @throws SequenceException if no sequence is found
     */
    @Cacheable(value = "Numbers", keyGenerator = "checksumKeyGenerator")
    public List<List<Integer>> getLongestSequenceOfDecreasingNumbers(String checksum, InputStream reader)
            throws IOException, SequenceException {
        return getLongestSequence(reader, OperationType.DECREASING_SEQUENCE);
    }

    private List<List<Integer>> getLongestSequence(InputStream stream, OperationType operation)
            throws IOException, SequenceException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
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
            } else if (subSequence.size() != 0) {
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

    private IntStream readFile(InputStream stream) {
        return (new BufferedReader(new InputStreamReader(stream))).lines().mapToInt(Integer::parseInt);
    }
}
