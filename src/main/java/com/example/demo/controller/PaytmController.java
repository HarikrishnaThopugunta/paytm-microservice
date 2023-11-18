package com.example.demo.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaytmController {

	
	
	@GetMapping("/paytm-train-list")
	public ResponseEntity<Object[]> getTrainList(){
		RestTemplate restTemplate = new RestTemplate();		
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("http://localhost:9090/getTrainList", Object[].class);
		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);	
		return responseEntity;
	}
	
}
