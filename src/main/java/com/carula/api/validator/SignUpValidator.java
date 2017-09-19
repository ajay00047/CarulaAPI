package com.carula.api.validator;

import org.springframework.stereotype.Component;

import com.carula.api.beans.SignUpRequestBean;
import com.carula.api.delegate.CustomValidator;
import com.carula.api.exception.MissingParameterException;
import com.carula.api.util.StringUtil;

@Component
public class SignUpValidator implements CustomValidator{

	@Override
	public void validate(Object obj) throws Exception {
		SignUpRequestBean bean = (SignUpRequestBean)obj;
		
		if(StringUtil.isNullOrBlank(bean.getFirstName()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getLastName()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getMobile()))
			throw new MissingParameterException();
	}

}
