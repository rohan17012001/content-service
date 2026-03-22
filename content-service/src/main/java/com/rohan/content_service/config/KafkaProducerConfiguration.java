package com.rohan.content_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohan.content_service.event.ContentCreatedEvent;

@Configuration
public class KafkaProducerConfiguration {

	@Bean
	public ProducerFactory<String, ContentCreatedEvent> contentCreatedEventProducerFactory(
			KafkaProperties kafkaProperties,
			ObjectMapper objectMapper) {
		Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties(null));
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.remove(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG);

		DefaultKafkaProducerFactory<String, ContentCreatedEvent> factory =
				new DefaultKafkaProducerFactory<>(props);
		JsonSerializer<ContentCreatedEvent> jsonSerializer = new JsonSerializer<>(objectMapper);
		factory.setValueSerializer(jsonSerializer);
		return factory;
	}

	@Bean
	@Primary
	public KafkaTemplate<String, ContentCreatedEvent> kafkaTemplate(
			ProducerFactory<String, ContentCreatedEvent> contentCreatedEventProducerFactory) {
		return new KafkaTemplate<>(contentCreatedEventProducerFactory);
	}
}
