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

    private Image createImage(String imageId, Long userId, Long groupId, List<ImageTag> imageTags, LocalDate date) {
        return Image.builder().id(imageId).userId(userId).groupId(groupId).tags(imageTags).date(date).build();
    }

    @BeforeEach
    void setUp() {
        Image image1 = createImage("image1", 1L, 2L, List.of(new ImageTag("tag1")), LocalDate.of(2021, 1, 1));
        Image image2 = createImage("image2", 1L, 2L, List.of(new ImageTag("tag1"), new ImageTag("tag2")), LocalDate.of(2019, 1, 1));
        Image image3 = createImage("image3", 1L, 3L, List.of(), LocalDate.of(2021, 2, 1));
        Image image4 = createImage("image4", 2L, 3L, List.of(new ImageTag("tag1")), LocalDate.of(2010, 1, 1));

        Mockito.when(imageRepository.findByGroupId(Mockito.eq(2L))).thenReturn(List.of(image1, image2));
        Mockito.when(imageRepository.findByGroupId(Mockito.eq(3L))).thenReturn(List.of(image3, image4));

        providerService = new ProviderServiceImpl(imageRepository);

    }

    @Test
    void findByGroupId() {
        ByGroupIdFinder byGroupIdFinder1 = new ByGroupIdFinder(2L);
        ByGroupIdFinder byGroupIdFinder2 = new ByGroupIdFinder(3L);

        assertEquals(2, providerService.find(byGroupIdFinder1).size());
        assertEquals(2, providerService.find(byGroupIdFinder2).size());
    }

    @Test
    void findByTag() {
        ByTagFinder byTagFinder1 = new ByTagFinder(List.of(2L), "tag1");
        ByTagFinder byTagFinder2 = new ByTagFinder(List.of(3L), "tag2");
        ByTagFinder byTagFinder3 = new ByTagFinder(List.of(2L, 3L), "tag1");

        assertEquals(2, providerService.find(byTagFinder1).size());
        assertEquals(0, providerService.find(byTagFinder2).size());
        assertEquals(3, providerService.find(byTagFinder3).size());
    }

    @Test
    void findByDate() {
        ByDateFinder byDateFinder1 = new ByDateFinder(List.of(2L), LocalDate.of(2020, 1, 1), LocalDate.of(2021, 10, 1));
        ByDateFinder byDateFinder2 = new ByDateFinder(List.of(2L, 3L), LocalDate.of(2020, 1, 1), LocalDate.of(2021, 10, 1));

        assertEquals(1, providerService.find(byDateFinder1).size());
        assertEquals(2, providerService.find(byDateFinder2).size());
    }

}