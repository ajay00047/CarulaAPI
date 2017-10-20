package com.carula.api.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.GetTripResponseBean;
import com.carula.api.beans.LatLng;
import com.carula.api.beans.Route;
import com.carula.api.beans.TripDetailsBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;
import com.carula.api.service.GoogleMapDirectionService;
import com.carula.api.util.Utils;

@Service
public class GetTripProcessor implements Processor {

	@Autowired
	private GetTripResponseBean dataBean;

	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception {
		TripSetUpRequestBean requestBean = (TripSetUpRequestBean) theRequestBean;

		int userId = userDAO.getUserIdFromPassKey(requestBean.getPassKey());
		int actualTripCount=0;

		requestBean.setUserId(userId);

		if (userId > 0) {
			List<TripDetailsBean> lstTrips = userDAO.getTrip(requestBean);
			List<TripDetailsBean> returnTrips = new ArrayList<TripDetailsBean>();
			if (!lstTrips.isEmpty()) {

				// filter trips
				for (TripDetailsBean trip : lstTrips) {
					List<LatLng> decodeLatLng = Utils.decodePolyLine(trip.getOverviewPolylines());
					LatLng point1 = new LatLng(requestBean.getTripDetailsBean().getStartLat(),
							requestBean.getTripDetailsBean().getStartLong());
					LatLng point2 = new LatLng(requestBean.getTripDetailsBean().getDropLat(),
							requestBean.getTripDetailsBean().getDropLong());
					LatLng closetStartPoint = null;
					LatLng closetDropPoint = null;
					int startPos = 1;
					int dropPos = 1;
					int i = 1;
					for (LatLng point : decodeLatLng) {
						if (null == closetStartPoint) {
							closetStartPoint = new LatLng(point.latitude, point.longitude,
									Utils.distFrom(point, point1));
							startPos = i;
						} else if (Utils.distFrom(point, point1) < closetStartPoint.distance) {
							closetStartPoint = new LatLng(point.latitude, point.longitude,
									Utils.distFrom(point, point1));
							startPos = i;
						}

						if (null == closetDropPoint) {
							closetDropPoint = new LatLng(point.latitude, point.longitude,
									Utils.distFrom(point, point2));
							dropPos = i;
						} else if (Utils.distFrom(point, point2) < closetDropPoint.distance) {
							closetDropPoint = new LatLng(point.latitude, point.longitude,
									Utils.distFrom(point, point2));
							dropPos = i;
						}
						i++;
					}

					if (dropPos > startPos && Utils.distFrom(closetStartPoint, closetDropPoint) > 2000) {
						System.out.println("Trip : " + trip.getTripId());
						System.out.println("Closet Start Point : " + closetStartPoint);
						System.out.println("Closet Drop Point : " + closetDropPoint);
						System.out.println("Travel Distance : " + Utils.distFrom(closetStartPoint, closetDropPoint));

						// get walking start polyline
						Route startRoute = getPolyLineFromPoints(point1, closetStartPoint);
						Route dropRoute = getPolyLineFromPoints(point2, closetDropPoint);
						if (startRoute != null) {
							
							if(startRoute.distance > 5000)
								continue;
							
							trip.setWalkDistanceStart(startRoute.distance);
							trip.setWalkDurationStart(startRoute.duration);
							trip.setWalkPolylinesStart(startRoute.overviewPolylines);
							/*
							 * List<LatLng> decodeLatLngStart =
							 * Utils.decodePolyLine(startRoute.overviewPolylines
							 * ); for (LatLng point : decodeLatLngStart) {
							 * System.out.println("Start Points : "+point); }
							 */
						}
						if (dropRoute != null) {
							
							if(dropRoute.distance > 5000)
								continue;
							
							trip.setWalkDistanceDrop(dropRoute.distance);
							trip.setWalkDurationDrop(dropRoute.duration);
							trip.setWalkPolylinesDrop(dropRoute.overviewPolylines);
							/*
							 * List<LatLng> decodeLatLngDrop =
							 * Utils.decodePolyLine(dropRoute.overviewPolylines)
							 * ; for (LatLng point : decodeLatLngDrop) {
							 * System.out.println("Drop Points : "+point); }
							 */
						}

						// add filtered trips
						if (startRoute != null && dropRoute != null){
							returnTrips.add(trip);
							actualTripCount++;
						}
					} 
						
				}
				if(actualTripCount == 0)
					dataBean.setErrorCode(ErrorCodes.CODE_202);
				else
					dataBean.setErrorCode(ErrorCodes.CODE_201);
				
				dataBean.setTrips(returnTrips);

			} else
				dataBean.setErrorCode(ErrorCodes.CODE_202);
		}

		dataBean.setType("getTrip");
		return dataBean;
	}

	private Route getPolyLineFromPoints(LatLng startPoint, LatLng dropPoint) {
		List<Route> lstRoutes = new GoogleMapDirectionService().getGoogleRoutes(startPoint, dropPoint);
		Route returnRoute = null;
		int shortestDistance = Integer.MAX_VALUE;
		for (Route route : lstRoutes) {
			if (route.distance < shortestDistance)
				returnRoute = route;
		}
		return returnRoute;
	}

}
