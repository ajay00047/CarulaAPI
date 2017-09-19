package com.carula.api.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSRequestBean
{
	private Messages[] Messages = new Messages[1];

	
    private Account Account= new Account();

    @JsonProperty("Messages")
    public Messages[] getMessages ()
    {
        return Messages;
    }

    @JsonProperty("Messages")
    public void setMessages (Messages[] Messages)
    {
        this.Messages = Messages;
    }

    @JsonProperty("Account")
    public Account getAccount ()
    {
        return Account;
    }

    @JsonProperty("Account")
    public void setAccount (Account Account)
    {
        this.Account = Account;
    }

    @Override
    public String toString()
    {
        return "SMSRequestBean [Messages = "+Messages+", Account = "+Account+"]";
    }
}
