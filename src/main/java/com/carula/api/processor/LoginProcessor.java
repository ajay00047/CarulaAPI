package com.carula.api.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.GenericErrorResponseBean;
import com.carula.api.beans.LoginRequestBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;
import com.carula.api.service.SMSService;
import com.carula.api.util.Utils;

@Service
public class LoginProcessor implements Processor {

	@Autowired
	private GenericErrorResponseBean dataBean;
	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception{
		LoginRequestBean requestBean = (LoginRequestBean) theRequestBean;
		String otp = Utils.generateOTP(6);
		
		
		
		if (!userDAO.mobileAlreadyPresent(requestBean.getMobile())) {
			dataBean.setErrorCode(ErrorCodes.CODE_701);
		} else {
			//dataBean = (LoginResponseDataBean)userDAO.getUserDetails(requestBean.getMobile());
			userDAO.updateOTP(otp,requestBean.getMobile());
			dataBean.setErrorCode(ErrorCodes.CODE_703);
			SMSService.SendOTP(otp,requestBean.getMobile());
		}

		dataBean.setType("login");
		return dataBean;
	}

}
