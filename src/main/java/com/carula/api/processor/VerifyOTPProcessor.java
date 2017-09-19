package com.carula.api.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.VerifyOTPRequestBean;
import com.carula.api.beans.VerifyOTPResponseDataBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;
import com.carula.api.exception.DBTransactionException;
import com.carula.api.service.PassKeyGeneratorService;

@Service
public class VerifyOTPProcessor implements Processor {

	@Autowired
	@Qualifier("verifyOTPResponseDataBean")
	private VerifyOTPResponseDataBean dataBean;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception {
		VerifyOTPRequestBean requestBean = (VerifyOTPRequestBean) theRequestBean;

		
		
		if (!userDAO.mobileAlreadyPresent(requestBean.getMobile())) {
			dataBean.setErrorCode(ErrorCodes.CODE_701);
		} else {
			if (userDAO.verifyOTP(requestBean.getOtp(), requestBean.getMobile())) {
				
				String passKey = PassKeyGeneratorService.generatepassKey(requestBean.getMobile());
				if (userDAO.updatePassKey(passKey, requestBean.getMobile())){
					dataBean = (VerifyOTPResponseDataBean)userDAO.getUserDetails(requestBean.getMobile());
					dataBean.setErrorCode(ErrorCodes.CODE_704);
					dataBean.setPassKey(passKey);
					userDAO.updateOTP(null, requestBean.getMobile());
				}
				else
					throw new DBTransactionException();

			} else
				dataBean.setErrorCode(ErrorCodes.CODE_705);
		}

		dataBean.setType("verifyOTP");
		return dataBean;
	}

}
