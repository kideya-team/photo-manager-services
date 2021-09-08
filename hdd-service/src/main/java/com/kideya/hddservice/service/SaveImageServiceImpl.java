package com.kideya.hddservice.service;

import com.kideya.hddservice.utils.FileFormat;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SaveImageServiceImpl implements SaveImageService {
    @Autowired
    private GettingImageInfoService gettingImageInfoService;

    @Value("${imageservice.tempFolder}")
    private String tempFolder;

    @Value("${imageservice.folderPrefix}")
    private String folderPrefix;

    @Override
    public void saveImage(String imageId, Long userId, String parameters) {
        String imagePath = gettingImageInfoService.getImagePath(imageId);
        if (imagePath == null) {
            throw new IllegalStateException();
        }


        try {
            File newImage = createFile(userId, imageId);
            downloadImage(imagePath, newImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @SneakyThrows
    private File createFile(Long userId, String imageId) {
        String userFolderPath = tempFolder + "\\" + folderPrefix + userId;
        Files.createDirectories(Paths.get(userFolderPath));

        String filePath = userFolderPath + "\\" + imageId + "." + FileFormat.JPG;

        File newFile = new File(filePath);
        newFile.createNewFile();
        return newFile;
    }
}
