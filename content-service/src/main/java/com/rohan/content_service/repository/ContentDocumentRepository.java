package com.rohan.content_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rohan.content_service.model.ContentDocument;

public interface ContentDocumentRepository extends MongoRepository<ContentDocument, String> {
}
