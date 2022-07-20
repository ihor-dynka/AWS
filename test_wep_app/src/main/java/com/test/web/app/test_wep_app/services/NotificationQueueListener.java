package com.test.web.app.test_wep_app.services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationQueueListener {

	private ImageQueueProducer imageQueueProducer;
	private EmailNotificationsService emailNotificationsService;

	@Scheduled(fixedRate = 3000)
	public void readQueueBatchAndSendToSns() {
		imageQueueProducer.readMessages()
			.forEach(message -> {
				emailNotificationsService.sendMessage(message.getBody());
			});
	}
}
