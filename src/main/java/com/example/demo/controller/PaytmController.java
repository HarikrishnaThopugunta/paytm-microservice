package com.example.demo.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.RequestContext;

@RestController
public class PaytmController {
	
	@GetMapping("/")
	public String healthCheck() {
		return "Paytm Service {healthy:true}";
	}
	
	@GetMapping("/paytm-train-list")
	public ResponseEntity<Object[]> getTrainList(){
		System.out.println("Request Received to Paytm App");
		RestTemplate restTemplate = new RestTemplate();		
		String env = System.getenv("IRCTC_MICROSERVICE_SERVICE_HOST");
		env = Objects.nonNull(env)? env :"http://localhost";
		System.out.println("env==============================>" + env);		
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(env +":9090/get-train-list", Object[].class);
		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);	
		return responseEntity;
	}
	
}
