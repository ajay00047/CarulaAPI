package com.carula.api.beans;

import org.springframework.stereotype.Component;


@Component("verifyOTPResponseDataBean")
public class VerifyOTPResponseDataBean extends GenericErrorResponseBean{


    private String firstName;
    private String lastName;
    private String mobile;
    private String dpPath;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDpPath() {
		return dpPath;
	}
	public void setDpPath(String dpPath) {
		this.dpPath = dpPath;
	}
    
    
    	
}
