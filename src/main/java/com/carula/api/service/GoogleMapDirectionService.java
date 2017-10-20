package com.carula.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carula.api.beans.LatLng;
import com.carula.api.beans.Route;
import com.carula.api.constants.Constants;
import com.carula.api.util.Utils;

public class GoogleMapDirectionService {

	private String createUrl(LatLng startPoint, LatLng dropPoint) throws UnsupportedEncodingException {
		String urlOrigin = URLEncoder.encode(startPoint.latitude + "," + startPoint.longitude, "utf-8");
		String urlDestination = URLEncoder.encode(dropPoint.latitude + "," + dropPoint.longitude, "utf-8");
		String postURL = Constants.GOOGLE_MAPS_DIRECTION_API + "origin=" + urlOrigin + "&destination=" + urlDestination
				+ "&mode=walking&avoid=indoor&alternatives=false" + "&key=" + Constants.GOOGLE_MAPS_DIRECTIONS_API_KEY;
		System.out.println("postURL : " + postURL);
		return postURL;
	}

	public List<Route> getGoogleRoutes(LatLng startPoint, LatLng dropPoint) {
		List<Route> lstRoutes = new ArrayList<Route>();
		try {
			String link = createUrl(startPoint, dropPoint);
			URL url = new URL(link);
			InputStream is = url.openConnection().getInputStream();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}

			//System.out.println("Google Direction Response " + buffer);
			lstRoutes = parseJSon(buffer.toString());
			if (lstRoutes == null)
				lstRoutes = new ArrayList<Route>();

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstRoutes;
	}

	private List<Route> parseJSon(String data) throws JSONException {
		if (data == null)
			return null;

		List<Route> routes = new ArrayList<Route>();
		JSONObject jsonData = new JSONObject(data);
		JSONArray jsonRoutes = jsonData.getJSONArray("routes");

		if (jsonRoutes.length() == 0)
			return null;

		for (int i = 0; i < jsonRoutes.length(); i++) {
			JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
			Route route = new Route();

			JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
			JSONArray jsonLegs = jsonRoute.getJSONArray("legs");

			int totalDistance = 0;
			int totalDuration = 0;

			for (int j = 0; j < jsonLegs.length(); j++) {

				JSONObject jsonLeg = jsonLegs.getJSONObject(j);

				JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
				JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
				JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
				JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

				totalDistance += jsonDistance.getInt("value");
				totalDuration += jsonDuration.getInt("value");

				if (j == 0) {
					route.startAddress = jsonLeg.getString("start_address");
					route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"),
							jsonStartLocation.getDouble("lng"));
				}

				if (j == jsonLegs.length() - 1) {
					route.endAddress = jsonLeg.getString("end_address");
					route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
					route.distance = totalDistance;
					route.duration = totalDuration;
				}

			}

			route.points = Utils.decodePolyLine(overview_polylineJson.getString("points"));
			route.overviewPolylines = overview_polylineJson.getString("points");
			routes.add(route);
		}
		return routes;
	}
}
