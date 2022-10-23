package com.example.numbersequenceprocessing.utils.checksum;

import com.example.numbersequenceprocessing.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class ChecksumUtils {
    private final FileUtils fileUtils;

    public ChecksumUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    /**
     * Calculating checksum (MD5) for a file located on the specified path
     * @param filePath path ot file
     * @return checksum MD5
     * @throws IOException if an error occurred while reading the file
     */
    public String getChecksum(String filePath) throws IOException {
        InputStream stream = fileUtils.readFile(filePath);
        return calculateChecksum(stream);
    }

    /**
     * Checksum calculation for a file sent over the network
     * @param file file sent via http
     * @return checksum MD5
     * @throws IOException if an error occurred while reading the file
     */
    public String getChecksum(MultipartFile file) throws IOException {
        InputStream stream = fileUtils.readFile(file);
        return calculateChecksum(stream);
    }

    private String calculateChecksum(InputStream stream) throws IOException {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
            return null;
        }

        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[8192];

        do {
            int read = stream.read(buffer);
            if(read <= 0)
                break;
            digest.update(buffer, 0, read);
        } while(true);

        byte[] sum = digest.digest();

        for (byte b : sum) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
