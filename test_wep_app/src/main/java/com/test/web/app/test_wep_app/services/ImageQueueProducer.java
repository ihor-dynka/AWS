package com.test.web.app.test_wep_app.services;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.test.web.app.test_wep_app.configuration.AwsConfig;
import com.test.web.app.test_wep_app.dto.images.ImageDetails;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageQueueProducer {

	private AmazonSQS amazonSQS;
	private AwsConfig awsConfig;

	public void sendMessageToQueue(ImageDetails imageDetails) {
		var request = new SendMessageRequest()
			.withQueueUrl(awsConfig.getSqsQueueUrl())
			.withMessageBody("Image has been uploaded. \n" +
				imageDetails.toString());


		amazonSQS.sendMessage(request);
	}

	public List<Message> readMessages() {
		return amazonSQS.receiveMessage(awsConfig.getSqsQueueUrl()).getMessages();
	}
}
