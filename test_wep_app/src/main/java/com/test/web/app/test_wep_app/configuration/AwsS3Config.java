package com.test.web.app.test_wep_app.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("aws")
@Configuration
@Data
public class AwsS3Config {

	private String accessKey;
	private String secretKey;
	private String s3BucketName;
}
