package com.rohan.content_service.service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.rohan.content_service.event.ContentCreatedEvent;

@Service
public class ContentCreatedEventProducer {

	public static final String CONTENT_CREATED_TOPIC = "content.created";

	private final KafkaTemplate<String, ContentCreatedEvent> kafkaTemplate;

	public ContentCreatedEventProducer(KafkaTemplate<String, ContentCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public CompletableFuture<SendResult<String, ContentCreatedEvent>> publish(ContentCreatedEvent event) {
		ContentCreatedEvent.Payload payload = Objects.requireNonNull(event.getPayload(), "payload");
		String contentId = Objects.requireNonNull(payload.getContentId(), "contentId");
		return kafkaTemplate.send(CONTENT_CREATED_TOPIC, contentId, event);
	}
}
