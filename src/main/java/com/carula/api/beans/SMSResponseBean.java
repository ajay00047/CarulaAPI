package com.carula.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMSResponseBean {
	private String ErrorMessage;

	private String JobId;

	private MessageData[] MessageData;

	private String ErrorCode;

	@JsonProperty("ErrorMessage")
	public String getErrorMessage() {
		return ErrorMessage;
	}

	@JsonProperty("ErrorMessage")
	public void setErrorMessage(String ErrorMessage) {
		this.ErrorMessage = ErrorMessage;
	}
	
	@JsonProperty("JobId")
	public String getJobId() {
		return JobId;
	}

	@JsonProperty("JobId")
	public void setJobId(String JobId) {
		this.JobId = JobId;
	}

	@JsonProperty("MessageData")
	public MessageData[] getMessageData() {
		return MessageData;
	}

	@JsonProperty("MessageData")
	public void setMessageData(MessageData[] MessageData) {
		this.MessageData = MessageData;
	}

	@JsonProperty("ErrorCode")
	public String getErrorCode() {
		return ErrorCode;
	}

	@JsonProperty("ErrorCode")
	public void setErrorCode(String ErrorCode) {
		this.ErrorCode = ErrorCode;
	}

	@Override
	public String toString() {
		return "SMSResponseBean [ErrorMessage = " + ErrorMessage + ", JobId = " + JobId + ", MessageData = " + MessageData
				+ ", ErrorCode = " + ErrorCode + "]";
	}

}
