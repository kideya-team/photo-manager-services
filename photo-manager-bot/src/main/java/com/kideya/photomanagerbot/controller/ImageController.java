package com.kideya.photomanagerbot.controller;

import com.kideya.photomanagerbot.dto.RawImageDto;
import com.kideya.photomanagerbot.services.GettingImageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/image")
public class ImageController {
    @Autowired
    private GettingImageService gettingImageService;

    @SneakyThrows
    @GetMapping(value="{imageId}")
    public String getImage(@PathVariable String imageId) {
        return gettingImageService.getImagePathById(imageId);
    }
}
