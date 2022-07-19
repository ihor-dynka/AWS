package com.test.web.app.test_wep_app.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationsService {

	private final SqsMessageProducer sqsMessageProducer;
}
