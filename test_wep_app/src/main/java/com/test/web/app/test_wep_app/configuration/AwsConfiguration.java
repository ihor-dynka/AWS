package com.test.web.app.test_wep_app.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AwsConfiguration {

	private AwsConfig awsConfig;

	@Bean
	public AmazonS3 getAmazonS3() {
		return AmazonS3ClientBuilder.standard()
			.withCredentials(getCredentialsProvider())
			.withRegion(Regions.US_EAST_1)
			.build();
	}

	@Bean
	public AmazonSQS getAmazonSQS() {
		return AmazonSQSClientBuilder.standard()
			.withCredentials(getCredentialsProvider())
			.withRegion(Regions.US_EAST_1)
			.build();
	}

	@Bean
	public AmazonSNS getAmazonSNS() {
		return AmazonSNSClientBuilder.standard()
			.withCredentials(getCredentialsProvider())
			.withRegion(Regions.US_EAST_1)
			.build();
	}

	@Bean
	public AWSLambdaAsync getAmazonLambdaClient() {
		return AWSLambdaAsyncClientBuilder.standard()
			.withCredentials(getCredentialsProvider())
			.withRegion(Regions.US_EAST_1)
			.build();
	}

	private AWSStaticCredentialsProvider getCredentialsProvider() {
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey()));
	}
}
