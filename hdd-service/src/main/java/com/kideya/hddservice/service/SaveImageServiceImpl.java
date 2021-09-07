package com.kideya.hddservice.service;

import com.kideya.hddservice.utils.FileFormat;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class SaveImageServiceImpl implements SaveImageService {
    @Autowired
    private GettingImageInfoService gettingImageInfoService;

    @Value("${imageservice.tempfolder}")
    private String tempFolder;

    @Override
    public void saveImage(String imageId, Long userId, String parameters) {
        String imagePath = gettingImageInfoService.getImagePath(imageId);
        if (imagePath == null) {
            throw new IllegalStateException();
        }

        downloadImage(imagePath, createFile(userId, imageId));
    }

    @SneakyThrows
    private void downloadImage(String sourceUrl, File targetFile) {
        URL imageUrl = new URL(sourceUrl);
        try (InputStream imageReader = new BufferedInputStream(
                imageUrl.openStream());
             OutputStream imageWriter = new BufferedOutputStream(
                     new FileOutputStream(targetFile));)
        {
            int readByte;

            while ((readByte = imageReader.read()) != -1)
            {
                imageWriter.write(readByte);
            }
        }
    }

    private File createFile(Long userId, String imageId) {
        return new File(tempFolder + "/" + userId + "/" + imageId + "." + FileFormat.JPG);
    }
}
