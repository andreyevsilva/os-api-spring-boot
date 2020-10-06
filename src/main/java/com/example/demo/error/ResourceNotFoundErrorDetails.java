package com.example.demo.error;

import java.time.OffsetDateTime;
import java.util.List;

public class ResourceNotFoundErrorDetails extends ErrorDetails {
	
	public static final class Builder {
		private String title;
		private Integer status;
		private String detail;
		private OffsetDateTime timestamp;
		private String developerMessage;
		private List<String> fields;
		private List<String> fieldsMessages;

		private Builder() {
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder timestamp(OffsetDateTime timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder developerMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}

		public Builder fields(List<String> fields) {
			this.fields = fields;
			return this;
		}

		public Builder fieldsMessages(List<String> fieldsMessages) {
			this.fieldsMessages = fieldsMessages;
			return this;
		}

		public ResourceNotFoundErrorDetails build() {
			ResourceNotFoundErrorDetails resourceNotFoundErrorDetails = new ResourceNotFoundErrorDetails();
			resourceNotFoundErrorDetails.setDeveloperMessage(developerMessage);
			resourceNotFoundErrorDetails.setTitle(title);
			resourceNotFoundErrorDetails.setDetail(detail);
			resourceNotFoundErrorDetails.setTimestamp(timestamp);
			resourceNotFoundErrorDetails.setStatus(status);
			return resourceNotFoundErrorDetails;
		}
	}
}

