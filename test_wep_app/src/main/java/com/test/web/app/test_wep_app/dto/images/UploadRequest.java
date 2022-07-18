package com.test.web.app.test_wep_app.dto.images;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {

	private String name;
	private MultipartFile file;
}
