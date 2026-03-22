package com.rohan.content_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rohan.content_service.model.ContentDocument;
import com.rohan.content_service.model.CreateContentRequest;
import com.rohan.content_service.service.ContentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/content")
public class ContentController {

	private final ContentService contentService;

	public ContentController(ContentService contentService) {
		this.contentService = contentService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContentDocument create(@Valid @RequestBody CreateContentRequest request) {
		ContentDocument document = new ContentDocument();
		document.setType(request.getType());
		document.setRawText(request.getRawText());
		document.setAuthorId(request.getAuthorId());
		document.setTeamId(request.getTeamId());
		return contentService.create(document);
	}
}
