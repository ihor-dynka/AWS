package com.test.web.app.test_wep_app.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test.web.app.test_wep_app.configuration.AwsConfig;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AwsS3Service {

	private AmazonS3 amazonS3;

	private AwsConfig awsConfig;

	@SneakyThrows
	public void uploadImage(String name, String customName, MultipartFile file) {
		checkIfBucketExists();

		var metadata = new ObjectMetadata();
		metadata.addUserMetadata("name", name);
		metadata.setContentType("image/jpg");

		var request = new PutObjectRequest(awsConfig.getS3BucketName(), customName, file.getInputStream(), metadata);
		request.setMetadata(metadata);
		amazonS3.putObject(request);
	}

	@SneakyThrows
	public void deleteImage(String name) {
		checkIfBucketExists();

		amazonS3.deleteObject(awsConfig.getS3BucketName(), name);
	}

	private void checkIfBucketExists() {
		if (!amazonS3.doesBucketExistV2(awsConfig.getS3BucketName())) {
			amazonS3.createBucket(awsConfig.getS3BucketName());
		}
	}
}
