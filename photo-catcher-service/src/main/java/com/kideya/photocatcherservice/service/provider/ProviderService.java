package com.kideya.photocatcherservice.service.provider;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.service.provider.finder.PhotoFinder;

import java.util.List;

public interface ProviderService {
    List<Image> find(PhotoFinder photoFinder);
}
