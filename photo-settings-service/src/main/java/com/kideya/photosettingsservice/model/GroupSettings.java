package com.kideya.photosettingsservice.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class GroupSettings {
    private long groupId;
    private String groupName;

    private boolean isActive;
}
