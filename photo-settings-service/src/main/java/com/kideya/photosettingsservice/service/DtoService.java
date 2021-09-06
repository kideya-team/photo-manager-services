package com.kideya.photosettingsservice.service;

import org.springframework.stereotype.Service;

@Service
public interface DtoService<T, DTO> {
	DTO toDto(T dataObject);
	T toDataObject(DTO dto);
}
