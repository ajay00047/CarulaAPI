package com.carula.api.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.GenericErrorResponseBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;

@Service
public class TripSetUpProcessor implements Processor {

	@Autowired
	private GenericErrorResponseBean dataBean;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception{
		TripSetUpRequestBean requestBean = (TripSetUpRequestBean) theRequestBean;
		
		int userId = userDAO.getUserIdFromPassKey(requestBean.getPassKey());
		
		requestBean.setUserId(userId);
		
		if(userId>0){
			if(userDAO.insertTrip(requestBean))
				dataBean.setErrorCode(ErrorCodes.CODE_007);
			else
				dataBean.setErrorCode(ErrorCodes.CODE_708);
		}

		dataBean.setType("tripSetUp");
		return dataBean;
	}

}
