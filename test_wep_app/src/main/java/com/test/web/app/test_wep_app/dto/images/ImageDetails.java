package com.test.web.app.test_wep_app.dto.images;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class ImageDetails {

	private long id;
	private String name;
	private long size;
	private String fileExtension;
	private String downloadUrl;
	private LocalDateTime updatedAt;
}
