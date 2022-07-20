package com.test.web.app.test_wep_app.services;

public interface INotificationService {

	void subscribe(String topicArn, String protocol, String endpoint);

	void unsubscribe(String topicArn);

	void sendMessage(String message);
}
