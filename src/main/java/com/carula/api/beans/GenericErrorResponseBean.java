package com.carula.api.beans;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.carula.api.constants.ErrorCodes;
import com.carula.api.delegate.DataBean;

@Primary
@Component
public class GenericErrorResponseBean implements DataBean{

	public ErrorCodes errorCode;
	public String errorMessage;
	private String passKey;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}

	public ErrorCodes getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodes errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getErrorMessage();
	}
}
