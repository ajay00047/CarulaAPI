package com.carula.api.util;

import com.carula.api.constants.Constants;

public class StringUtil {

	public static String[] getErrorMessage(String iMessage) {
		System.out.println(iMessage);
		String[] sArray = new String[2];

		try {
			if(iMessage!=null && iMessage.contains(Constants.INVALID_PASSKEY)){
				sArray[0] = "888";
				sArray[1] = Constants.INVALID_PASSKEY;
			}
			else if(iMessage!=null && iMessage.startsWith(Constants.VALIDATION_ERROR)){
				int startPos = iMessage.indexOf("[");
				String newMessage = iMessage.substring(startPos);
				int endPos = (newMessage).indexOf("]");
				sArray[0] = "400";
				sArray[1] = newMessage.substring(1, endPos);
			}
			else if (iMessage!=null && iMessage.startsWith(Constants.VALIDATION_FAILED)) {
				int startPos = iMessage.indexOf("codes [");
				String newMessage = iMessage.substring(startPos);
				int endPos = (newMessage).indexOf(".");
				sArray[0] = "706";
				sArray[1] = newMessage.substring(7, endPos);
			} else {
				sArray[0] = "400";
				sArray[1] = "Unable to process request";
			}

		} catch (Exception e) {
			e.printStackTrace();
			sArray[0] = "500";
			sArray[1] = "Internal Server Error";
		}
		return sArray;
	}
	
	public static boolean isNullOrBlank(String str){
		return (null == str ? true:("".equals(str.trim()) ? true:false));
	}

}
