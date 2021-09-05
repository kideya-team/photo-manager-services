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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {
    @Mock
    private ImageRepository imageRepository;

    private ProviderService providerService;

    private Image createImage(String imageId, int userId, int groupId, List<ImageTag> imageTags, LocalDate date) {
        return new Image(imageId, userId, groupId, imageTags, date);
    }

    @BeforeEach
    void setUp() {
        Image image1 = createImage("image1", 1, 2, List.of(new ImageTag("tag1")), LocalDate.of(2021, 1, 1));
        Image image2 = createImage("image2", 1, 2, List.of(new ImageTag("tag1"), new ImageTag("tag2")), LocalDate.of(2019, 1, 1));
        Image image3 = createImage("image3", 1, 3, List.of(), LocalDate.of(2021, 2, 1));
        Image image4 = createImage("image4", 2, 3, List.of(new ImageTag("tag1")), LocalDate.of(2010, 1, 1));

        Mockito.when(imageRepository.findByUserId(Mockito.eq(1))).thenReturn(List.of(image1, image2, image3));
        Mockito.when(imageRepository.findByUserId(Mockito.eq(2))).thenReturn(List.of(image4));

        providerService = new ProviderServiceImpl(imageRepository);

    }

    @Test
    void findByGroupId() {
        ByGroupIdFinder byGroupIdFinder1 = new ByGroupIdFinder(1, 2);
        ByGroupIdFinder byGroupIdFinder2 = new ByGroupIdFinder(1, 3);
        ByGroupIdFinder byGroupIdFinder3 = new ByGroupIdFinder(2, 3);

        assertEquals(2, providerService.find(byGroupIdFinder1).size());
        assertEquals(1, providerService.find(byGroupIdFinder2).size());
        assertEquals(1, providerService.find(byGroupIdFinder3).size());
    }

    @Test
    void findByTag() {
        ByTagFinder byTagFinder1 = new ByTagFinder(1, "tag1");
        ByTagFinder byTagFinder2 = new ByTagFinder(1, "tag2");
        ByTagFinder byTagFinder3 = new ByTagFinder(2, "tag1");

        assertEquals(2, providerService.find(byTagFinder1).size());
        assertEquals(1, providerService.find(byTagFinder2).size());
        assertEquals(1, providerService.find(byTagFinder3).size());
    }

    @Test
    void findByDate() {
        ByDateFinder byDateFinder1 = new ByDateFinder(1, LocalDate.of(2020, 1, 1), LocalDate.of(2021, 10, 1));
        ByDateFinder byDateFinder2 = new ByDateFinder(2, LocalDate.of(2020, 1, 1), LocalDate.of(2021, 10, 1));

        assertEquals(2, providerService.find(byDateFinder1).size());
        assertEquals(0, providerService.find(byDateFinder2).size());
    }

}