package com.rohan.content_service.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
@Document(collection = "content")
public class ContentDocument {

	@Id
	private String id;

	private String contentId;

	private String type;

	private String rawText;

	private String authorId;

	private String teamId;

	private Instant createdAt;

	public ContentDocument() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "ContentDocument{" + "id='" + id + '\'' + ", contentId='" + contentId + '\'' + ", type='" + type + '\''
				+ ", rawText='" + rawText + '\'' + ", authorId='" + authorId + '\'' + ", teamId='" + teamId + '\''
				+ ", createdAt=" + createdAt + '}';
	}
}
