package com.rohan.content_service.model;

import jakarta.validation.constraints.NotBlank;

public class CreateContentRequest {

	@NotBlank(message = "type is required")
	private String type;

	@NotBlank(message = "rawText is required")
	private String rawText;

	@NotBlank(message = "authorId is required")
	private String authorId;

	@NotBlank(message = "teamId is required")
	private String teamId;

	public CreateContentRequest() {
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

	@Override
	public String toString() {
		return "CreateContentRequest{" + "type='" + type + '\'' + ", rawText='" + rawText + '\'' + ", authorId='"
				+ authorId + '\'' + ", teamId='" + teamId + '\'' + '}';
	}
}
