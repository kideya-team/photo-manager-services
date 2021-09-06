package com.kideya.photosettingsservice.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class GroupSettings {
    private int groupId;
    private String groupName;

    private boolean isActive;
}
