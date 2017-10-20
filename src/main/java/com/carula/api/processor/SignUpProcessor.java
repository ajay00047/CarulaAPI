package com.carula.api.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.GenericErrorResponseBean;
import com.carula.api.beans.SignUpRequestBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;
import com.carula.api.service.SMSService;
import com.carula.api.util.Utils;

@Service
public class SignUpProcessor implements Processor {

	@Autowired
	private GenericErrorResponseBean dataBean;
	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception{
		SignUpRequestBean requestBean = (SignUpRequestBean) theRequestBean;
		String otp = Utils.generateOTP(6);

		if(requestBean.getNoSignUp() == true){
			userDAO.updateName(requestBean);
			dataBean.setErrorCode(ErrorCodes.CODE_007);
		}else if (userDAO.mobileAlreadyPresent(requestBean.getMobile())) {
			dataBean.setErrorCode(ErrorCodes.CODE_700);
			
		} else {
			userDAO.insertUser(requestBean);
			userDAO.updateOTP(otp,requestBean.getMobile());
			dataBean.setErrorCode(ErrorCodes.CODE_702);
			SMSService.SendOTP(otp,requestBean.getMobile());
		}

		dataBean.setType("signUp");
		return dataBean;
	}

}
