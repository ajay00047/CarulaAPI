package com.carula.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account
{
    private String User;

    private String DCS;

    private String Channel;

    private String Password;

    private String SenderId;

    private String GroupId;

    private String SchedTime;

    @JsonProperty("User")
    public String getUser ()
    {
        return User;
    }

    @JsonProperty("User")
    public void setUser (String User)
    {
        this.User = User;
    }

    @JsonProperty("DCS")
    public String getDCS ()
    {
        return DCS;
    }

    @JsonProperty("DCS")
    public void setDCS (String DCS)
    {
        this.DCS = DCS;
    }

    @JsonProperty("Channel")
    public String getChannel ()
    {
        return Channel;
    }

    @JsonProperty("Channel")
    public void setChannel (String Channel)
    {
        this.Channel = Channel;
    }

    @JsonProperty("Password")
    public String getPassword ()
    {
        return Password;
    }

    @JsonProperty("Password")
    public void setPassword (String Password)
    {
        this.Password = Password;
    }

    @JsonProperty("SenderId")
    public String getSenderId ()
    {
        return SenderId;
    }

    @JsonProperty("SenderId")
    public void setSenderId (String SenderId)
    {
        this.SenderId = SenderId;
    }

    @JsonProperty("GroupId")
    public String getGroupId ()
    {
        return GroupId;
    }

    @JsonProperty("GroupId")
    public void setGroupId (String GroupId)
    {
        this.GroupId = GroupId;
    }

    @JsonProperty("SchedTime")
    public String getSchedTime ()
    {
        return SchedTime;
    }

    @JsonProperty("SchedTime")
    public void setSchedTime (String SchedTime)
    {
        this.SchedTime = SchedTime;
    }

    @Override
    public String toString()
    {
        return "Account [User = "+User+", DCS = "+DCS+", Channel = "+Channel+", Password = "+Password+", SenderId = "+SenderId+", GroupId = "+GroupId+", SchedTime = "+SchedTime+"]";
    }
}
