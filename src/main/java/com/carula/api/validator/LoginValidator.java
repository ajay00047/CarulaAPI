package com.carula.api.validator;

import org.springframework.stereotype.Component;

import com.carula.api.beans.LoginRequestBean;
import com.carula.api.delegate.CustomValidator;
import com.carula.api.exception.MissingParameterException;
import com.carula.api.util.StringUtil;

@Component
public class LoginValidator implements CustomValidator{

	@Override
	public void validate(Object obj) throws Exception {
		LoginRequestBean bean = (LoginRequestBean)obj;
		
		if(StringUtil.isNullOrBlank(bean.getMobile()))
			throw new MissingParameterException();
	}

}
