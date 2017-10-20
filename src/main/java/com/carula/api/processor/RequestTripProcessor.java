package com.carula.api.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.GenericErrorResponseBean;
import com.carula.api.beans.GetTripResponseBean;
import com.carula.api.beans.LatLng;
import com.carula.api.beans.RequestTripBean;
import com.carula.api.beans.Route;
import com.carula.api.beans.TripDetailsBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;
import com.carula.api.service.GoogleMapDirectionService;
import com.carula.api.util.Utils;

@Service
public class RequestTripProcessor implements Processor {

	@Autowired
	private GenericErrorResponseBean dataBean;

	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception {
		RequestTripBean requestBean = (RequestTripBean) theRequestBean;

		int userId = userDAO.getUserIdFromPassKey(requestBean.getPassKey());

		requestBean.setUserId(userId);

		if(userId>0){
			
			requestBean.getTripDetailsBean().setStartDateTime(Utils.convertSQLDateString2Timestamp(requestBean.getTripDetailsBean().getStartDateTime()));;
			
			if("DONE".equals(userDAO.requestTrip(requestBean)))
				dataBean.setErrorCode(ErrorCodes.CODE_007);
			else if("N_NO_SEATS".equals(userDAO.requestTrip(requestBean)))
				dataBean.setErrorCode(ErrorCodes.CODE_709);
			else if("N_ALREADY_REQUESTED".equals(userDAO.requestTrip(requestBean)))
				dataBean.setErrorCode(ErrorCodes.CODE_710);
			else
				dataBean.setErrorCode(ErrorCodes.CODE_400);
				
		}

		dataBean.setType("requestTrip");
		return dataBean;
	}

}
