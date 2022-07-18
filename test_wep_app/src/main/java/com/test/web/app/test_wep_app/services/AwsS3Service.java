package com.test.web.app.test_wep_app.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test.web.app.test_wep_app.configuration.AwsS3Config;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AwsS3Service {

	private AmazonS3 amazonS3;

	private AwsS3Config awsS3Config;

	@SneakyThrows
	public void uploadImage(String name, String customName, MultipartFile file) {
		checkIfBucketExists();

		var metadata = new ObjectMetadata();
		metadata.addUserMetadata("name", name);
		metadata.setContentType("image/jpg");

		var request = new PutObjectRequest(awsS3Config.getS3BucketName(), customName, file.getInputStream(), metadata);
		request.setMetadata(metadata);
		amazonS3.putObject(request);
	}

	@SneakyThrows
	public void deleteImage(String name) {
		checkIfBucketExists();

		amazonS3.deleteObject(awsS3Config.getS3BucketName(), name);
	}

	private void checkIfBucketExists() {
		if (!amazonS3.doesBucketExistV2(awsS3Config.getS3BucketName())) {
			amazonS3.createBucket(awsS3Config.getS3BucketName());
		}
	}
}
