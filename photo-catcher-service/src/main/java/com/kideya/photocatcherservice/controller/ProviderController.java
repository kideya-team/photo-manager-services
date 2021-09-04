package com.kideya.photocatcherservice.controller;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/provider")
public class ProviderController {
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/getAll")
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @GetMapping("/getByGroupId/{id}")
    public List<Image> getByGroupId(@PathVariable int id) {
        return imageRepository.findByGroupId(id);
    }
}
