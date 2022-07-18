package com.test.web.app.test_wep_app.mappers;

import com.test.web.app.test_wep_app.dto.images.ImageDetails;
import com.test.web.app.test_wep_app.entities.ImageEntity;

public class ImageMapper {

	public static ImageDetails toImageDetails(ImageEntity imageEntity) {
		return ImageDetails.builder()
			.id(imageEntity.getId())
			.name(imageEntity.getName())
			.fileExtension(imageEntity.getFileExtension())
			.size(imageEntity.getSize())
			.updatedAt(imageEntity.getUpdatedAt())
			.build();
	}
}
