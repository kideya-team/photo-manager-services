package com.kideya.photocatcherservice.controller;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import com.kideya.photocatcherservice.service.provider.ProviderService;
import com.kideya.photocatcherservice.service.provider.finder.ByDateFinder;
import com.kideya.photocatcherservice.service.provider.finder.ByGroupIdFinder;
import com.kideya.photocatcherservice.service.provider.finder.ByTagFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/{userId}/byTag")
    public List<Image> getAllImages(@PathVariable int userId, @RequestParam(name="tag") String tag) {
        ByTagFinder byTagFinder = new ByTagFinder(userId, tag);

        return providerService.find(byTagFinder);
    }

    @GetMapping("/{userId}/byGroupId")
    public List<Image> getByGroupId(@PathVariable int userId, @RequestParam(name="groupId") int groupId) {
        ByGroupIdFinder byGroupIdFinder = new ByGroupIdFinder(userId, groupId);

        return providerService.find(byGroupIdFinder);
    }

    @GetMapping("/{userId}/byDate")
    public List<Image> getByDate(@PathVariable int userId,
                                 @RequestParam(name="fromDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
                                 @RequestParam(name="toDate", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        if (toDate == null) {
            toDate = LocalDateTime.now();
        }

        ByDateFinder byDateFinder = new ByDateFinder(userId, fromDate, toDate);

        return providerService.find(byDateFinder);
    }
}
