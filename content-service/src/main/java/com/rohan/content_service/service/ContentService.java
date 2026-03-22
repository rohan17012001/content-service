package com.rohan.content_service.service;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.rohan.content_service.event.ContentCreatedEvent;
import com.rohan.content_service.model.ContentDocument;
import com.rohan.content_service.repository.ContentDocumentRepository;

@Service
public class ContentService {

	private static final String CONTENT_CREATED_EVENT_TYPE = "CONTENT_CREATED";
	private static final int KAFKA_SEND_TIMEOUT_SECONDS = 10;

	private final ContentDocumentRepository contentDocumentRepository;
	private final ContentCreatedEventProducer contentCreatedEventProducer;

	public ContentService(ContentDocumentRepository contentDocumentRepository,
			ContentCreatedEventProducer contentCreatedEventProducer) {
		this.contentDocumentRepository = contentDocumentRepository;
		this.contentCreatedEventProducer = contentCreatedEventProducer;
	}

	public ContentDocument create(ContentDocument document) {
		Objects.requireNonNull(document, "document");

		document.setId(null);
		document.setContentId(UUID.randomUUID().toString());
		document.setCreatedAt(Instant.now());

		ContentDocument saved = contentDocumentRepository.save(document);

		ContentCreatedEvent event = toContentCreatedEvent(saved);
		contentCreatedEventProducer.publish(event)
				.orTimeout(KAFKA_SEND_TIMEOUT_SECONDS, TimeUnit.SECONDS)
				.join();

		return saved;
	}

	private static ContentCreatedEvent toContentCreatedEvent(ContentDocument saved) {
		ContentCreatedEvent.Payload payload = new ContentCreatedEvent.Payload();
		payload.setContentId(saved.getContentId());
		payload.setContentType(saved.getType());
		payload.setAuthorId(saved.getAuthorId());
		payload.setTeamId(saved.getTeamId());

		ContentCreatedEvent event = new ContentCreatedEvent();
		event.setEventType(CONTENT_CREATED_EVENT_TYPE);
		event.setEventId(UUID.randomUUID().toString());
		event.setOccurredAt(saved.getCreatedAt());
		event.setPayload(payload);
		return event;
	}
}
