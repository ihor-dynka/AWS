package com.test.web.app.test_wep_app.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("aws")
@Configuration
@Data
public class AwsConfig {

	private String accessKey;
	private String secretKey;
	private String s3BucketName;
	private String sqsRegion;
	private String snsTopicArn;
	private String sqsQueueUrl;
}
