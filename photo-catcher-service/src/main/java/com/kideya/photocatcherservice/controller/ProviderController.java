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

    @GetMapping("/{userId}/byTag")
    public List<Image> getByTag(@PathVariable Long userId, @RequestParam(name="tag") String tag) {
        return imageService.getByTag(userId, tag);
    }

    @GetMapping("/{userId}/byGroupId")
    public List<Image> getByGroupId(@PathVariable Long userId, @RequestParam(name="groupId") Long groupId) {
        return imageService.getByGroupId(userId, groupId);
    }

    @GetMapping("/{userId}/byDate")
    public List<Image> getByDate(@PathVariable Long userId,
                                 @RequestParam(name="fromDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDate fromDate,
                                 @RequestParam(name="toDate", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDate toDate) {
        return imageService.getByDate(userId, fromDate, toDate);
    }
}
