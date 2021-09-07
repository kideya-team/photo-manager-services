package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.model.ConstraintsSettings;
import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupsServiceTest {
    @MockBean
    private SettingsRepository settingsRepository;

    @Autowired
    private GroupsService groupsService;

    private ConstraintsSettings createConstraintsSettingsByGroupIds(List<Long> groupIds) {
        return ConstraintsSettings.builder()
                .groupIds(groupIds)
                .build();
    }

    private Settings createSettingsByUserIdAndGroupIds(Long userId, List<Long> groupIds) {
        return Settings.builder().
                userId(userId)
                .constraintsSettings(createConstraintsSettingsByGroupIds(groupIds))
                .build();

    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void getUserIdsByGroupId() {
        Settings set1 = createSettingsByUserIdAndGroupIds(1L, List.of(1L, 7L, 3L));
        Settings set2 = createSettingsByUserIdAndGroupIds(2L, List.of(3L, 5L, 7L));
        Settings set3 = createSettingsByUserIdAndGroupIds(3L, List.of(7L, 8L, 9L));

        Mockito.when(settingsRepository.findByUserId(Mockito.eq(1L))).thenReturn(set1);
        Mockito.when(settingsRepository.findByUserId(Mockito.eq(2L))).thenReturn(set2);
        Mockito.when(settingsRepository.findByUserId(Mockito.eq(3L))).thenReturn(set3);

        Mockito.when(settingsRepository.findAll()).thenReturn(List.of(set1, set2, set3));


        assertEquals(groupsService.getUserIdsByGroupId(3L), List.of(1L, 2L));
        assertEquals(groupsService.getUserIdsByGroupId(8L), List.of(3L));
        assertEquals(groupsService.getUserIdsByGroupId(7L), List.of(1L, 2L, 3L));
        assertEquals(groupsService.getUserIdsByGroupId(10L), List.of());
    }
}