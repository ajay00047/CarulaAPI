package com.carula.api.beans;

public class MyTripsRequestBean extends BaseRequestBean {

	private String iam;
	private int page;
	private long tripId;
	
	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public String getIam() {
		return iam;
	}

	public void setIam(String iam) {
		this.iam = iam;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
