package com.carula.api.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	
	/*public static void main(String...strings){
		System.out.println(getCurrentPlusTime());
	}*/
	
    public static String getCurrentPlusTime() {

        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);
        String currentTime = "";
        try {
            currentTime = sdf1.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTime;
    }
}

