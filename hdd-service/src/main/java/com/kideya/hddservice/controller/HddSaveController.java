package com.kideya.hddservice.controller;

import com.kideya.hddservice.dto.PhotoServiceInfo;
import com.kideya.hddservice.service.SaveImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/hdd")
public class HddSaveController {
    @Autowired
    private SaveImageService saveImageService;

    @PostMapping(path = "/save")
    public ResponseEntity<PhotoServiceInfo> saveImage(@RequestBody PhotoServiceInfo serviceInfo) {
        saveImageService.saveImage(serviceInfo.getImageId(), serviceInfo.getUserId(), serviceInfo.getParameters());

        return new ResponseEntity<>(serviceInfo, HttpStatus.CREATED);
    }

}
