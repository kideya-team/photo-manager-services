package com.kideya.hddservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawImageDto {
    private int height;
    private int width;
    private byte[] data;
}
