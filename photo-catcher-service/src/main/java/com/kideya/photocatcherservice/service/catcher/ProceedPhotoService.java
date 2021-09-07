package com.kideya.photocatcherservice.service.catcher;

import com.kideya.photocatcherservice.dto.ServiceSettingsDto;
import com.kideya.photocatcherservice.model.Image;
import com.netflix.discovery.shared.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProceedPhotoService {
    @Autowired
    private ImageSaverService imageSaverService;

    @Autowired
    private ServicesGetterService servicesGetterService;

    @Autowired
    private PhotoSenderService photoSenderService;

    public ProceedPhotoService(ImageSaverService imageSaverService) {
        this.imageSaverService = imageSaverService;
    }

    public void proceed(Image image) {
        imageSaverService.saveImageToDataBase(image);
        List<Pair<Long, ServiceSettingsDto>> services = servicesGetterService.getServicesToSend(image);
        photoSenderService.send(services, image);
    }

    public List<Image> showImages() {
        return imageSaverService.getImages();
    }
}