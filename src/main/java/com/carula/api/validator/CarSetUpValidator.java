package com.carula.api.validator;

import org.springframework.stereotype.Component;

import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.delegate.CustomValidator;
import com.carula.api.exception.MissingParameterException;
import com.carula.api.util.StringUtil;

@Component
public class CarSetUpValidator implements CustomValidator{

	@Override
	public void validate(Object obj) throws Exception {
		CarSetUpRequestBean bean = (CarSetUpRequestBean)obj;
		
		if(StringUtil.isNullOrBlank(bean.getCompany()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getModel()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getColor()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getNo()))
			throw new MissingParameterException();
	}

}
