package com.kideya.photosettingsservice.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ConstraintsSettings {
    private List<GroupSettings> groupSettings;
    private List<BannedUserSettings> bannedUserSettings;
}
