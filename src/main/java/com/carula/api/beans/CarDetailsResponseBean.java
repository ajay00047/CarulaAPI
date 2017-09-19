package com.carula.api.beans;

import org.springframework.stereotype.Component;

@Component("carDetailsResponseBean")
public class CarDetailsResponseBean extends GenericErrorResponseBean{

	private String company;
    private String model;
    private String color;
    private String no;
    
	public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

	
}
