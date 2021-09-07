package com.kideya.photosettingsservice.model;

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
    private List<GroupSettings> groupSettings = new ArrayList<>();
    private List<Long> bannedUserIds = new ArrayList<>();
}
