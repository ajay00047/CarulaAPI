package com.carula.api.beans;

public class SignUpRequestBean extends BaseRequestBean{

	private String firstName;
    private String lastName;
    private String mobile;
    private boolean noSignUp;

    public boolean getNoSignUp() {
        return noSignUp;
    }

    public void setNoSignUp(boolean noSignUp) {
        this.noSignUp = noSignUp;
    }

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

}
