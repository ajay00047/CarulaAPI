package com.carula.api.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.carula.api.beans.LatLng;

public class Utils {

	public static String generateOTP(int len) {
		// Using numeric values
		String numbers = "0123456789";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}

		return new String(otp);
	}

	public static String generateRandomString(int len) {
		// Using numeric values
		String numbers = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		// Using random method
		Random rndm_method = new Random();

		char[] otp = new char[len];

		for (int i = 0; i < len; i++) {
			// Use of charAt() method : to get character value
			// Use of nextInt() as it is scanning the value as int
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}

		return new String(otp);
	}

	public static String generateMd5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

	public static String convertJavaDate2Sqldate(String dateTime) {

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMMM, yyyy hh:mm a");
		String currentTime="";
		Date dt;
		try {
			dt = sdf1.parse(dateTime);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			currentTime = sdf.format(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime;
	}
	
	
	public static String addDate2Seoncds(String dateTime,int seconds){
		String currentTime="";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy hh:mm a");
		try {
			
			calendar.setTime(sdf.parse(dateTime));
			calendar.add(Calendar.SECOND, seconds);
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			currentTime = sdf1.format(calendar.getTime());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime;
	}
	
	public static List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
	
	public static int distFrom(LatLng point1,LatLng point2) {
	    double earthRadius = 6371000; //meters
	    double lat1 = point1.latitude;
	    double lng1 = point1.longitude;
	    double lat2 = point2.latitude;
	    double lng2 = point2.longitude;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    int dist = (int) (earthRadius * c);

	    return dist;
	    }
}