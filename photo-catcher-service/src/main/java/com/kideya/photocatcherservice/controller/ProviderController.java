package com.kideya.photocatcherservice.controller;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public List<Image> getAllImages(@PathVariable int userId) {
        return imageRepository.findByUserId(userId);
    }

    @GetMapping("/{userId}/byGroupId")
    public List<Image> getByGroupId(@PathVariable int userId, @RequestParam(name="groupId") int groupId) {
        return imageRepository.findByUserId(userId);
    }
}
