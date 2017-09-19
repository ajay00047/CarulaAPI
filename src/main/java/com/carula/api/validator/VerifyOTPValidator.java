package com.carula.api.validator;

import org.springframework.stereotype.Component;

import com.carula.api.beans.VerifyOTPRequestBean;
import com.carula.api.delegate.CustomValidator;
import com.carula.api.exception.MissingParameterException;
import com.carula.api.util.StringUtil;

@Component
public class VerifyOTPValidator implements CustomValidator{

	@Override
	public void validate(Object obj) throws Exception {
		VerifyOTPRequestBean bean = (VerifyOTPRequestBean)obj;
		
		if(StringUtil.isNullOrBlank(bean.getMobile()))
			throw new MissingParameterException();
		
		if(StringUtil.isNullOrBlank(bean.getOtp()))
			throw new MissingParameterException();
	}

}
