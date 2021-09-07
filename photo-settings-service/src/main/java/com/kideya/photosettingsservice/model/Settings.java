package com.kideya.photosettingsservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings {
    @Id
    private Long userId;

    private List<ServiceSettings> serviceSettings = new ArrayList<>();
    private ConstraintsSettings constraintsSettings = new ConstraintsSettings();
}
