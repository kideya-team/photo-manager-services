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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @GetMapping("/{userId}/byTag")
    public List<Image> getAllImages(@PathVariable Long userId, @RequestParam(name="tag") String tag) {
        ByTagFinder byTagFinder = new ByTagFinder(List.of(), tag);

        return providerService.find(byTagFinder);
    }

    @GetMapping("/{userId}/byGroupId")
    public List<Image> getByGroupId(@PathVariable Long userId, @RequestParam(name="groupId") Long groupId) {
        ByGroupIdFinder byGroupIdFinder = new ByGroupIdFinder(groupId);

        return providerService.find(byGroupIdFinder);
    }

    @GetMapping("/{userId}/byDate")
    public List<Image> getByDate(@PathVariable Long userId,
                                 @RequestParam(name="fromDate") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDate fromDate,
                                 @RequestParam(name="toDate", required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDate toDate) {
        if (toDate == null) {
            toDate = LocalDate.now();
        }

        ByDateFinder byDateFinder = new ByDateFinder(List.of(), fromDate, toDate);

        return providerService.find(byDateFinder);
    }
}
