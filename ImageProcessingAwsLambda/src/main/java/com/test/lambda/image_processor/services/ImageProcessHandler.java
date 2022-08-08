package com.test.lambda.image_processor.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImageProcessHandler implements RequestHandler<Map<String, Object>, APIGatewayProxyResponseEvent> {
	private static final String SQS_QUEUE_URL = System.getenv("sqs_queue_url");
	private static final String SNS_TOPIC_ARN = System.getenv("sns_topic_arn");
	private static final AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard()
		.withRegion(Regions.US_EAST_1)
		.build();
	private static final AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard()
		.withRegion(Regions.US_EAST_1)
		.build();
	private static final int MAX_NUMBER_OF_MESSAGES = 5;
	private static final int WAIT_TIME_SECONDS = 5;
	private LambdaLogger lambdaLogger;

	@Override
	public APIGatewayProxyResponseEvent handleRequest(Map<String, Object> input, Context context) {
		lambdaLogger = context.getLogger();

		Object detail = input.get("detail-type");
		String detailType = detail == null ? "API" : String.valueOf(detail);

		lambdaLogger.log("Handled Request for ARN = " + SNS_TOPIC_ARN
			+ "; Request Source = " + detailType
			+ "; Function Name = " + context.getFunctionName()
			+ "; Remaining Time in millis = " + context.getRemainingTimeInMillis());

		var messages = receiveMessages();

		if (messages.isEmpty()) {
			lambdaLogger.log("No messages found. \n");
		} else {
			publishMessages(messages);
			deleteMessage(messages);
		}

		return new APIGatewayProxyResponseEvent()
			.withStatusCode(200)
			.withBody("")
			.withIsBase64Encoded(false);
	}

	private void deleteMessage(List<Message> messages) {
		messages.forEach(message -> {
			lambdaLogger.log(" Deleting message with id = " + message.getMessageId());
			amazonSQS.deleteMessage(SQS_QUEUE_URL, message.getReceiptHandle());
		});
	}

	private void publishMessages(List<Message> messages) {
		var joinedMessage = messages
			.stream()
			.map(Message::getBody)
			.collect(Collectors.joining("\n \n"));

		var publishRequest = new PublishRequest()
			.withTopicArn(SNS_TOPIC_ARN)
			.withSubject("Processed SQS Queue Messages")
			.withMessage(joinedMessage);

		lambdaLogger.log("Publishing messages " + joinedMessage);
		amazonSNS.publish(publishRequest);
	}

	private List<Message> receiveMessages() {
		var receiveMessageRequest = new ReceiveMessageRequest()
			.withQueueUrl(SQS_QUEUE_URL)
			.withMaxNumberOfMessages(MAX_NUMBER_OF_MESSAGES)
			.withWaitTimeSeconds(WAIT_TIME_SECONDS);

		return IntStream.of(0, MAX_NUMBER_OF_MESSAGES)
			.mapToObj(i -> amazonSQS.receiveMessage(receiveMessageRequest).getMessages())
			.flatMap(Collection::stream)
			.collect(Collectors.toList());
	}
}
