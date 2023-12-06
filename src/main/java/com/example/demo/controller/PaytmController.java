package com.example.demo.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.resilience.PaytmCircuitBreakerRegistry;

@RestController()	
public class PaytmController {
	
	@Autowired
	private PaytmCircuitBreakerRegistry paytmCircuitBreakerRegistry;
	
	@GetMapping("/paytm-microservice")
	public String healthCheck() {
		return "Paytm Service {healthy:true}";
	}
	
	@GetMapping("/paytm-microservice/paytm-train-list")
	public ResponseEntity<Object[]> getTrainList(){
		System.out.println("Request Received to Paytm App");
		RestTemplate restTemplate = new RestTemplate();		
		String env = System.getenv("IRCTC_MICROSERVICE_SERVICE_HOST");
		env = Objects.nonNull(env)? env+"/irctc-microservice" :"http://localhost";
		System.out.println("env==============================>" + env);		
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(env +":9090/get-train-list", Object[].class);
		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);	
		return responseEntity;
	}
	
	@GetMapping("/paytm-microservice/paytm-train-list-with-resilience")
	public ResponseEntity<Object[]> getTrainListWithResilienec(){
		System.out.println("Request Received to Paytm App");
		
		return paytmCircuitBreakerRegistry.getPaytmTrainList();
	}
	
}
