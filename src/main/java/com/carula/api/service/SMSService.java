package com.carula.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.carula.api.beans.SMSResponseBean;
import com.carula.api.constants.Constants;

public class SMSService {

	@Autowired

	public static void SendOTP(String otp, String mobile) throws Exception {
/*		ObjectMapper mapper = new ObjectMapper();
		SMSRequestBean smsBean = new SMSRequestBean();

		Messages[] msgBeans = new Messages[1];
		Messages msgBean = new Messages();*/

		String msg = String.format(Constants.SMS_OTP_TEMPLATE, new Object[] { Constants.SMS_APPNAME, otp });
		System.out.println("msg : "+msg);
/*		// assign variables
		smsBean.getAccount().setUser(Constants.SMS_SENDER_USER);
		smsBean.getAccount().setPassword(Constants.SMS_SENDER_PASSWORD);
		smsBean.getAccount().setSenderId(Constants.SMS_SENDER_ID);
		smsBean.getAccount().setChannel(Constants.SMS_CHANNEL);
		smsBean.getAccount().setDCS(Constants.SMS_DCS);
		smsBean.getAccount().setSchedTime(Constants.SMS_SchedTime);
		smsBean.getAccount().setGroupId(Constants.SMS_GroupId);

		msgBean.setNumber(mobile);
		msgBean.setText(msg);

		msgBeans[0] = msgBean;

		smsBean.setMessages(msgBeans);

		// Object to JSON in String
		String json = mapper.writeValueAsString(smsBean);
		System.out.println(json);*/

		MultiValueMap<String,String> map = new LinkedMultiValueMap<String,String>();
		map.add("APIKey", Constants.SMS_API_CODE);
		map.add("senderid", Constants.SMS_SENDER_ID);
		map.add("channel", Constants.SMS_CHANNEL);
		map.add("DCS", Constants.SMS_DCS);
		map.add("flashsms", Constants.SMS_FLASHSMS);
		map.add("number", mobile);
		map.add("route", "1");
		
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(Constants.SMS_HTTP_URL).queryParams(map);
		
		System.out.println(builder.toUriString());
		
		// post the sms to api
		RestTemplate restTemplate = new RestTemplate();
		SMSResponseBean smsResponse = restTemplate.getForObject(builder.toUriString()+"&text="+msg,SMSResponseBean.class);
		System.out.println("otp : "+otp+ " smsResponse : " + smsResponse);
	}

}
