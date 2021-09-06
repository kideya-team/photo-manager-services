package com.kideya.photosettingsservice.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class BannedUserSettings {
    private int userId;
    private String userName;
}
