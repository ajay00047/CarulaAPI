package com.carula.api.beans;

public class BaseRequestBean {

	private String passKey;
	private String appPassCode;
	private int userId;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAppPassCode() {
		return appPassCode;
	}

	public void setAppPassCode(String appPassCode) {
		this.appPassCode = appPassCode;
	}

	public String getPassKey() {
		return passKey;
	}

	public void setPassKey(String passKey) {
		this.passKey = passKey;
	}

}
