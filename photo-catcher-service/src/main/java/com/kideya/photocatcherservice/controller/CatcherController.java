package com.kideya.photocatcherservice.controller;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.model.ImageTag;
import com.kideya.photocatcherservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/catcher")
public class CatcherController {
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/add")
    public String addImage() {
        Image image = Image.builder().id("imageId").userId(3L).groupId(4L).tags(List.of(new ImageTag("tagg"))).date(LocalDate.now()).build();
        imageRepository.insert(image);
        return "Added";
    }
}

