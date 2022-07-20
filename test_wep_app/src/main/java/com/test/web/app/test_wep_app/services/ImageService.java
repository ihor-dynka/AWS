package com.test.web.app.test_wep_app.services;

import com.test.web.app.test_wep_app.exceptions.ObjectNotFoundException;
import com.test.web.app.test_wep_app.dto.images.ImageDetails;
import com.test.web.app.test_wep_app.entities.ImageEntity;
import com.test.web.app.test_wep_app.mappers.ImageMapper;
import com.test.web.app.test_wep_app.repositories.ImagesRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

	private ImagesRepository imagesRepository;

	private AwsS3Service awsS3Service;

	private ImageQueueProducer imageQueueProducer;

	public List<ImageDetails> getAll() {
		return imagesRepository.findAll()
			.stream()
			.map(ImageMapper::toImageDetails)
			.collect(Collectors.toList());
	}

	public ImageDetails getRandomImage() {
		return ImageMapper.toImageDetails(imagesRepository.findRandomEntity().get());
	}

	@SneakyThrows
	public ImageDetails getImageByName(String name) {
		var imageEntity = imagesRepository.findImageEntitiesByName(name)
			.orElseThrow(() -> new ObjectNotFoundException(name));
		return ImageMapper.toImageDetails(imageEntity);
	}

	public boolean existsByName(String name) {
		return imagesRepository.existsByName(name);
	}

	@Transactional
	@SneakyThrows
	public ImageDetails uploadImage(String name, String customName, MultipartFile file) {
		var url = awsS3Service.uploadImage(name, customName, file);

		var imageEntity = imagesRepository.saveAndFlush(ImageEntity.builder()
			.name(name)
			.fileExtension(FileUtils.extension(Objects.requireNonNull(file.getOriginalFilename())))
			.size(file.getSize())
			.downloadUrl(url)
			.updatedAt(LocalDateTime.now())
			.build());

		var imageDetails = ImageMapper.toImageDetails(imageEntity);

		imageQueueProducer.sendMessageToQueue(imageDetails);

		return imageDetails;
	}

	@Transactional
	public void deleteImage(String name) {
		awsS3Service.deleteImage(name);

		var imageEntity = imagesRepository.findImageEntitiesByName(name);

		imageEntity.ifPresent(entity -> imagesRepository.delete(entity));
	}
}
