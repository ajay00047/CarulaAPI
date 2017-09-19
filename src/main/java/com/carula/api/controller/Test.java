package com.carula.api.controller;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
/*	public static void main(String...strings){
		String polyLine = "qqhtB}ol|LwKp@oN~@AYnCUvIi@pR{@bBDnFz@|@HlDL`ALnFjAXBX?f@QPOPWL_@VuBp@uId@{EVk@JGVIZBVPJT@X_@rCSbBe@pG]hEMfCBdATj@Zj@lAvAlHvHtDpD|SzTbDjDlFxFbH~H~HtIdAbAp@f@rBlArB|@hBl@x@P~@NrAJlD?xAE|YaCzOsAjQiEhJ}B~HkBbEgA~Ai@bCuA~GoC|Ai@vA]fEm@hEy@dB]bAMb@CrAA|Hb@hAN~Dx@nA\\n@RtAv@bFlCfKlGrNvIpFfDhc@fW|AdAxLlHp@`@@@JFh@\\pNpI`DfBrrAjr@xFzCrv@p`@dChA~Bz@hGhBnC`AnCx@bM|DtiA~]rZbJdGjBxXnIjVlHzPlFzBn@vQ~FnIlC|Af@bBx@vA`AhFbElDlClBjBxPzM`BrAb@b@zB`BfFnEbKjIrAjAtAnA|@~@hCbDjL`QzIlOlFlJbBjC\\f@LDPNjE|HXb@N`@Dt@Hx@`@v@lFbJ~@pBvBxD`@t@\\fANhADx@E~@YnByG``@Q`AWt@]n@iE|EuMjKkCzBwFrEiAhA{CbDq@|@_@z@Yx@Mr@I|CM~AUbAa@|@gBpCy@lAEp@mA~CaArBmAzBlBpAr@f@KJuCiBKGg@[NUP[";
		List<LatLng> decodeLatLng = decodePolyLine(polyLine);
		LatLng point2 = new LatLng(19.204580, 72.962154);
		int i = 1;
		for(LatLng point:decodeLatLng){
			System.out.println(i++ + "\tLatLng : "+point.latitude+","+point.longitude+" Distance : "+distFrom(point,point2));
		}
	}*/

	private static List<LatLng> decodePolyLine(final String poly) {
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

class LatLng {
    public final double latitude;
    public final double longitude;

    public LatLng(double var1, double var3) {
        if(-180.0D <= var3 && var3 < 180.0D) {
            this.longitude = var3;
        } else {
            this.longitude = ((var3 - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
        }

        this.latitude = Math.max(-90.0D, Math.min(90.0D, var1));
    }


    public int hashCode() {
        long var3 = Double.doubleToLongBits(this.latitude);
        int var2 = 31 + (int)(var3 ^ var3 >>> 32);
        var3 = Double.doubleToLongBits(this.longitude);
        var2 = 31 * var2 + (int)(var3 ^ var3 >>> 32);
        return var2;
    }

    public boolean equals(Object var1) {
        if(this == var1) {
            return true;
        } else if(!(var1 instanceof LatLng)) {
            return false;
        } else {
            LatLng var2 = (LatLng)var1;
            return Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(var2.latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(var2.longitude);
        }
    }

    public String toString() {
        double var1 = this.latitude;
        double var3 = this.longitude;
        return (new StringBuilder(60)).append("lat/lng: (").append(var1).append(",").append(var3).append(")").toString();
    }
}
