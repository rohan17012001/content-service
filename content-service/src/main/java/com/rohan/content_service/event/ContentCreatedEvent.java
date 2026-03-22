package com.rohan.content_service.event;

import java.time.Instant;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
public class ContentCreatedEvent {

	private String eventType;
	private String eventId;
	private Instant occurredAt;
	private Payload payload;

	public ContentCreatedEvent() {
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Instant getOccurredAt() {
		return occurredAt;
	}

	public void setOccurredAt(Instant occurredAt) {
		this.occurredAt = occurredAt;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "ContentCreatedEvent{" + "eventType='" + eventType + '\'' + ", eventId='" + eventId + '\''
				+ ", occurredAt=" + occurredAt + ", payload=" + payload + '}';
	}

	public static class Payload {

		private String contentId;
		private String contentType;
		private String authorId;
		private String teamId;

		public Payload() {
		}

		public String getContentId() {
			return contentId;
		}

		public void setContentId(String contentId) {
			this.contentId = contentId;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
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
			return "Payload{" + "contentId='" + contentId + '\'' + ", contentType='" + contentType + '\''
					+ ", authorId='" + authorId + '\'' + ", teamId='" + teamId + '\'' + '}';
		}
	}
}
