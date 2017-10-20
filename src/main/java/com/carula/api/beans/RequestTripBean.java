package com.carula.api.beans;

public class RequestTripBean extends BaseRequestBean{

	private TripDetailsBean tripDetailsBean;

	public TripDetailsBean getTripDetailsBean() {
		return tripDetailsBean;
	}

	public void setTripDetailsBean(TripDetailsBean tripDetailsBean) {
		this.tripDetailsBean = tripDetailsBean;
	}
	
	
}
