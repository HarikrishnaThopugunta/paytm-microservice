package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.demo.entities.TrakingEntity;

@Configuration
@ComponentScan(basePackages = { "com.example.demo" })
public class MakeMyTripKafkaConsumerConfig {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListerContainerFactory(
			ConsumerFactory<String, Object> consumerFactory) {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
		factory.getContainerProperties().setMissingTopicsFatal(false);
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}

	@Bean
	public ConsumerFactory<String, Object> consumerFactory(
			@org.springframework.beans.factory.annotation.Value("${bootstrap-server}") String boostrapServer) {
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServer);
//		config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//		config.put(org.springframework.kafka.support.serializer.JsonDeserializer.VALUE_DEFAULT_TYPE,
//				TrakingEntity.class.getCanonicalName());
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
		config.put(JsonDeserializer.KEY_DEFAULT_TYPE, TrakingEntity.class.getCanonicalName());
		config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
		config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TrakingEntity.class.getCanonicalName());
		config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		
		
		
		return new DefaultKafkaConsumerFactory<String, Object>(config);
	}

}
