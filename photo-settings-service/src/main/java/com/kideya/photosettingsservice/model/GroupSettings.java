package com.kideya.photosettingsservice.model;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupSettings {
    private Long groupId;
    private String groupName;

    private boolean isActive;
}
