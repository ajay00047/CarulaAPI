package com.carula.api.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Messages
{
    private String Text;

    private String Number;

    @JsonProperty("Text")
    public String getText ()
    {
        return Text;
    }

    @JsonProperty("Text")
    public void setText (String Text)
    {
        this.Text = Text;
    }

    @JsonProperty("Number")
    public String getNumber ()
    {
        return Number;
    }

    @JsonProperty("Number")
    public void setNumber (String Number)
    {
        this.Number = Number;
    }

    @Override
    public String toString()
    {
        return "Messages [Text = "+Text+", Number = "+Number+"]";
    }
}
