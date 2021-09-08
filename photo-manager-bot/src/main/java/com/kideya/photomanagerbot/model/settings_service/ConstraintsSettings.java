package com.kideya.photomanagerbot.model.settings_service;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConstraintsSettings {
    private List<Long> groupIds = new ArrayList<>();
    private List<Long> bannedUserIds = new ArrayList<>();
}
