package com.kideya.hddservice.service;

import com.kideya.hddservice.dto.RawImageDto;
import com.kideya.hddservice.utils.FileFormat;
import com.kideya.hddservice.utils.ServiceName;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

@Service
public class SaveImageServiceImpl implements SaveImageService {
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${imageservice.tempfolder}")
    private String tempFolder;

    private String getServicePrefix(String serviceName) {
        InstanceInfo photoSettings = eurekaClient.getApplications().getInstancesBySecureVirtualHostName(serviceName).get(0);
        return "http://" + photoSettings.getHostName() + ":" + photoSettings.getPort();
    }

    @Override
    public void saveImage(String imageId, Long userId, String parameters) {
        RawImageDto rawImage = restTemplate.getForObject(getServicePrefix(ServiceName.PHOTO_BOT) + "/api/image/{imageId}", RawImageDto.class, imageId);
        if (rawImage == null) {
            throw new IllegalStateException();
        }

        BufferedImage image = createImage(rawImage.getData(), rawImage.getWidth(), rawImage.getHeight());
        saveToFile(image, createFile(userId, imageId));
    }

    private BufferedImage createImage(byte[] data, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        byte[] imageData = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        System.arraycopy(data, 0, imageData, 0, data.length);
        return image;
    }

    @SneakyThrows
    private void saveToFile(BufferedImage image, File file) {
        ImageIO.write(image, FileFormat.JPG, file);
    }

    private File createFile(Long userId, String imageId) {
        return new File(tempFolder + "/" + userId + "/" + imageId + "." + FileFormat.JPG);
    }
}
