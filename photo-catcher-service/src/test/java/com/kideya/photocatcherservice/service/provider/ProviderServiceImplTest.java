package com.kideya.photocatcherservice.service.provider;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.model.ImageTag;
import com.kideya.photocatcherservice.repository.ImageRepository;
import com.kideya.photocatcherservice.service.provider.finder.ByDateFinder;
import com.kideya.photocatcherservice.service.provider.finder.ByGroupIdFinder;
import com.kideya.photocatcherservice.service.provider.finder.ByTagFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {
    @Mock
    private ImageRepository imageRepository;

    private ProviderService providerService;

    private Image createImage(String imageId, int userId, int groupId, List<ImageTag> imageTags, LocalDateTime date) {
        return new Image(imageId, userId, groupId, imageTags, date);
    }

    @BeforeEach
    void setUp() {
        Image image1 = createImage("image1", 1, 2, List.of(new ImageTag("tag1")), LocalDateTime.of(2021, 1, 1, 1, 1));
        Image image2 = createImage("image2", 1, 2, List.of(), LocalDateTime.of(2019, 1, 1, 1, 1));
        Image image3 = createImage("image3", 1, 3, List.of(), LocalDateTime.of(2021, 2, 1, 1, 1));

        Mockito.when(imageRepository.findByUserId(Mockito.eq(1))).thenReturn(List.of(image1, image2, image3));

        providerService = new ProviderServiceImpl(imageRepository);

    }

    @Test
    void findByGroupId() {
        ByGroupIdFinder byGroupIdFinder = new ByGroupIdFinder(1, 2);

        assertEquals(2, providerService.find(byGroupIdFinder).size());
    }

    @Test
    void findByTag() {
        ByTagFinder byTagFinder = new ByTagFinder(1, "tag1");

        assertEquals(1, providerService.find(byTagFinder).size());
    }

    @Test
    void findByDate() {
        ByDateFinder byDateFinder = new ByDateFinder(1, LocalDateTime.of(2020, 1, 1, 1, 1), LocalDateTime.now());

        assertEquals(2, providerService.find(byDateFinder).size());
    }

}