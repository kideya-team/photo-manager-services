package com.kideya.photomanagerbot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RawImageDto {
    private int height;
    private int width;
    private byte[] data;
}
