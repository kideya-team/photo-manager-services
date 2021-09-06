package com.kideya.photomanagerbot.controller;

import com.kideya.photomanagerbot.PhotoTelegramBot;
import com.kideya.photomanagerbot.services.GettingImageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/image")
public class ImageController {
    @Autowired
    private GettingImageService gettingImageService;

    @SneakyThrows
    @GetMapping(value="{imageId}", produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable String imageId) {
        return gettingImageService.getImageDataById(imageId);
    }
}
