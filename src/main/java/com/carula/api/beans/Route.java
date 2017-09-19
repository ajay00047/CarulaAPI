package com.carula.api.beans;

import java.util.ArrayList;
import java.util.List;

public class Route {
	public int distance;
	public int duration;
	public String endAddress;
	public LatLng endLocation;
	public String startAddress;
	public LatLng startLocation;
	public List<String> wayPointAddresses;
	public List<LatLng> wayPointLocations;
	public List<LatLng> points;
	public String overviewPolylines;

	public Route() {
		wayPointAddresses = new ArrayList<>();
		wayPointLocations = new ArrayList<>();
	}
}
