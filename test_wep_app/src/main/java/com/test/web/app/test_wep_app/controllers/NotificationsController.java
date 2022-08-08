package com.test.web.app.test_wep_app.controllers;

import com.test.web.app.test_wep_app.services.AwsLambda;
import com.test.web.app.test_wep_app.services.EmailNotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationsController {

	private EmailNotificationsService emailNotificationsService;
	private AwsLambda awsLambda;

	@PostMapping("/subscription")
	@ResponseStatus(HttpStatus.OK)
	public void subscribe(@RequestParam String email) {
		emailNotificationsService.subscribe(email);
	}

	@DeleteMapping("/subscription")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unsubscribe(@RequestParam String email) {
		emailNotificationsService.unsubscribe(email);
	}

	@PostMapping("/send")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void send(){
		awsLambda.invoke();
	}
}
