package com.example.demo.resilience;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Service
public class PaytmCircuitBreakerRegistry {

	CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
			.slidingWindowType(SlidingWindowType.COUNT_BASED)
		    .failureRateThreshold(50)
		    .waitDurationInOpenState(Duration.ofMillis(1000))
		    .permittedNumberOfCallsInHalfOpenState(2)
		    .slidingWindowSize(10)
		    .recordExceptions(IOException.class, TimeoutException.class)
		    //.ignoreExceptions(BusinessException.class, OtherBusinessException.class)
		    .build();
	
	// Create a CircuitBreakerRegistry with a custom global configuration
	CircuitBreakerRegistry circuitBreakerRegistry =
	  CircuitBreakerRegistry.of(circuitBreakerConfig);
	
	CircuitBreaker circuitBreaker = circuitBreakerRegistry
			  .circuitBreaker("paytm-cricuit-breaker");
	
	private Object[] getTrainList() {
		RestTemplate restTemplate = new RestTemplate();			
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("http://localhost:9090/get-train-list", Object[].class);
		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);
		return objects;
	}
	
	public ResponseEntity<Object[]> getPaytmTrainList() {
		RestTemplate restTemplate = new RestTemplate();			
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("http://localhost:9090/get-train-list", Object[].class);
		Object[] objects = responseEntity.getBody();
		Arrays.asList(objects).forEach(System.out::println);	
		
		Supplier<Object[]> decoratedSupplier = CircuitBreaker
			    .decorateSupplier(circuitBreaker, this::getTrainList);

		Object[] result = circuitBreaker
				  .executeSupplier(this::getTrainList);
		return responseEntity;
	}
	
	
}