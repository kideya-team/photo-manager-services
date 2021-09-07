package com.kideya.photocatcherservice.controller;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.service.catcher.ProceedPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catcher")
public class CatcherController {
    @Autowired
    private ProceedPhotoService proceedPhotoService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/images")
    public List<Image> getListOfImages() {
        return proceedPhotoService.showImages();
    }

    @PostMapping("/photo")
    public void proceedPhoto(@RequestBody Image image) {
        proceedPhotoService.proceed(image);
    }
}

