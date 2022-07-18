package com.test.web.app.test_wep_app.controllers;

import com.test.web.app.test_wep_app.dto.images.ImageDetails;
import com.test.web.app.test_wep_app.services.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImagesController {

	private ImageService imageService;

	@GetMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ImageDetails getImageByName(@PathVariable String name) {
		return imageService.getImageByName(name);
	}

	@GetMapping("/random")
	@ResponseStatus(HttpStatus.OK)
	public ImageDetails getRandomImage() {
		return imageService.getRandomImage();
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ImageDetails> getAll() {
		return imageService.getAll();
	}

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.OK)
	public ImageDetails upload(@RequestParam String name, @RequestParam MultipartFile file) {
		return imageService.uploadImage(name, name, file);
	}

	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteImage(@PathVariable String name) {
		if (imageService.existsByName(name)) {
			imageService.deleteImage(name);
		}
	}
}
