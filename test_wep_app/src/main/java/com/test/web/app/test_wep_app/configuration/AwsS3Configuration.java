package com.test.web.app.test_wep_app.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AwsS3Configuration {

	private AwsS3Config awsConfig;

	@Bean
	public AmazonS3 getAmazonS3() {
		return AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey())))
			.withRegion(Regions.US_EAST_1)
			.build();
	}
}
