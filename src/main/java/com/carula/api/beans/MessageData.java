package com.carula.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageData {
	private String MessageId;

	private String Number;

	@JsonProperty("MessageId")
	public String getMessageId() {
		return MessageId;
	}

	@JsonProperty("MessageId")
	public void setMessageId(String MessageId) {
		this.MessageId = MessageId;
	}

	@JsonProperty("Number")
	public String getNumber() {
		return Number;
	}

	@JsonProperty("Number")
	public void setNumber(String Number) {
		this.Number = Number;
	}

	@Override
	public String toString() {
		return "MessageData [MessageId = " + MessageId + ", Number = " + Number + "]";
	}
}