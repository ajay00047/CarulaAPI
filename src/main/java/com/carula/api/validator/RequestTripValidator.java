package com.carula.api.validator;

import org.springframework.stereotype.Component;

import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.RequestTripBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.delegate.CustomValidator;
import com.carula.api.exception.MissingParameterException;
import com.carula.api.util.StringUtil;

@Component
public class RequestTripValidator implements CustomValidator{

	@Override
	public void validate(Object obj) throws Exception {
		RequestTripBean bean = (RequestTripBean)obj;

		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getStart()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getDrop()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getOverviewPolylines()))
			throw new MissingParameterException();
		
		
		
	}

}
