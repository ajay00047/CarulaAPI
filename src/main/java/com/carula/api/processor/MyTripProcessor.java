package com.carula.api.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.GetTripResponseBean;
import com.carula.api.beans.MyTripsRequestBean;
import com.carula.api.beans.TripDetailsBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;

@Service
public class MyTripProcessor implements Processor {

	@Autowired
	private GetTripResponseBean dataBean;

	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception {
		MyTripsRequestBean requestBean = (MyTripsRequestBean) theRequestBean;

		int userId = userDAO.getUserIdFromPassKey(requestBean.getPassKey());
		requestBean.setUserId(userId);

		if (userId > 0) {
			List<TripDetailsBean> lstTrips = userDAO.getMyTrips(requestBean);
			if (!lstTrips.isEmpty()) {

				dataBean.setErrorCode(ErrorCodes.CODE_201);
				dataBean.setTrips(lstTrips);

			} else
				dataBean.setErrorCode(ErrorCodes.CODE_202);
		}

		dataBean.setType("myTrip");
		return dataBean;
	}

}
