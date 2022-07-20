package com.test.web.app.test_wep_app.services;

import com.amazonaws.services.sns.AmazonSNS;
import com.test.web.app.test_wep_app.configuration.AwsConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailNotificationsService implements INotificationService {

	private AwsConfig awsConfig;
	private AmazonSNS amazonSNS;

	@Override
	public void subscribe(String topicArn, String protocol, String endpoint) {
		amazonSNS.subscribe(awsConfig.getSnsTopicArn(), protocol, endpoint);
	}

	public void subscribe(String endpoint) {
		amazonSNS.subscribe(awsConfig.getSnsTopicArn(), "email", endpoint);
	}

	@Override
	public void unsubscribe(String endpoint) {
		var subscriptionArn =
			amazonSNS.listSubscriptions().getSubscriptions()
				.stream()
				.filter(subscription ->
					subscription.getProtocol().equals("email")
						&& subscription.getEndpoint().equals(endpoint)
				).findFirst()
				.orElseThrow(() -> new RuntimeException("No subscription found."))
				.getSubscriptionArn();

		amazonSNS.unsubscribe(subscriptionArn);
	}

	@Override
	public void sendMessage(String message) {
		amazonSNS.publish(awsConfig.getSnsTopicArn(), message);
	}
}
