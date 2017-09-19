package com.carula.api.validator;

import org.springframework.stereotype.Component;

import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.delegate.CustomValidator;
import com.carula.api.exception.MissingParameterException;
import com.carula.api.util.StringUtil;

@Component
public class GetTripValidator implements CustomValidator{

	@Override
	public void validate(Object obj) throws Exception {
		TripSetUpRequestBean bean = (TripSetUpRequestBean)obj;
		
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getDate()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getTime()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getStart()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getDrop()))
			throw new MissingParameterException();
		if(StringUtil.isNullOrBlank(bean.getTripDetailsBean().getOverviewPolylines()))
			throw new MissingParameterException();
		
		
		
	}

}
