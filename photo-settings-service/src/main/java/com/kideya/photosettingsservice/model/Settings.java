package com.kideya.photosettingsservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings {
    private List<ServiceSettings> serviceSettings;
    private ConstraintsSettings constraintsSettings;
    @Id
    private int userId;
}
