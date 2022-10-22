package com.example.numbersequenceprocessing.utils.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Component
public class FileUtils {
    /**
     * Reads a file located on the disk at the specified path
     * @param filePath path to local file
     * @return file reader
     * @throws IOException if something goes wrong while reading a file
     */
    public BufferedReader readFile(String filePath) throws IOException {
        return Files.newBufferedReader(new File(filePath).toPath());
    }

    /**
     * Reads a file transferred from the network
     * @param file {@link MultipartFile}
     * @return file reader
     * @throws IOException if something goes wrong while reading a file
     */
    public BufferedReader readFile(MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        return new BufferedReader(new InputStreamReader(is));
    }
}
