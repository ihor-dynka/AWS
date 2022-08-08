package com.test.web.app.test_wep_app.services;

import com.amazonaws.services.lambda.model.InvokeRequest;
import com.test.web.app.test_wep_app.configuration.AwsConfig;
import com.test.web.app.test_wep_app.configuration.AwsConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AwsLambda {

	private AwsConfig awsConfig;
	private AwsConfiguration awsConfiguration;

	public void invoke() {
		var invokeRequest = new InvokeRequest()
			.withFunctionName(awsConfig.getLambdaFunctionName())
			.withPayload("");
		var invokeResult = awsConfiguration.getAmazonLambdaClient()
			.invoke(invokeRequest);

		log.info("Result invoking " + awsConfig.getLambdaFunctionName() + ": " + invokeResult);
	}
}
