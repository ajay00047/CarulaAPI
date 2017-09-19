package com.carula.api.beans;

import java.util.List;

import org.springframework.stereotype.Component;

@Component("getTripResponseBean")
public class GetTripResponseBean extends GenericErrorResponseBean{

	private List<TripDetailsBean> trips;

	public List<TripDetailsBean> getTrips() {
		return trips;
	}

	public void setTrips(List<TripDetailsBean> trips) {
		this.trips = trips;
	}
	
	

}
