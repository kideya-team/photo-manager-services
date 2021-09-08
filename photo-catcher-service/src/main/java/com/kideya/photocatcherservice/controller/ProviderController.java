package com.kideya.photocatcherservice.controller;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import com.kideya.photocatcherservice.service.provider.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/provider")
public class ProviderController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/all")
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @GetMapping("/{userId}/photoByTag")
    public List<Image> getByTag(@PathVariable Long userId, @RequestParam(name="tag") String tag) {
        return imageService.getByTag(userId, tag);
    }

    @GetMapping("/{userId}/photoByGroup")
    public List<Image> getByGroupId(@PathVariable Long userId, @RequestParam(name="groupId") Long groupId) {
        return imageService.getByGroupId(userId, groupId);
    }

    @GetMapping("/{userId}/byDate")
    public List<Image> getByDate(@PathVariable Long userId,
                                 @RequestParam(name="to_date") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fromDate,
                                 @RequestParam(name="from_date", required=false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate toDate) {
        return imageService.getByDate(userId, fromDate, toDate);
    }
}
