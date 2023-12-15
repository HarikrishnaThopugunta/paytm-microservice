package com.example.demo.kafkalistner;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
public class TrainTrackingHandler {

	@KafkaListener(id = "paytmConsumerClient", topics = "17235", groupId = "paytm-consumer-group")
	public void listner(String payload) {
		System.out.println("Message Received " + payload);
	}

}
