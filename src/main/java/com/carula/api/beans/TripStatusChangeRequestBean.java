package com.carula.api.beans;

public class TripStatusChangeRequestBean extends BaseRequestBean {

	private String action;
	private long tripId;
	private long tripRequestId;
	private String status;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public long getTripRequestId() {
		return tripRequestId;
	}

	public void setTripRequestId(long tripRequestId) {
		this.tripRequestId = tripRequestId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
