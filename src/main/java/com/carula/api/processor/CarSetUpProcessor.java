package com.carula.api.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.GenericErrorResponseBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;

@Service
public class CarSetUpProcessor implements Processor {

	@Autowired
	private GenericErrorResponseBean dataBean;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception{
		CarSetUpRequestBean requestBean = (CarSetUpRequestBean) theRequestBean;
		
		int userId = userDAO.getUserIdFromPassKey(requestBean.getPassKey());
		
		requestBean.setUserId(userId);
		
		if(userId>0){
			if(userDAO.insertCar(requestBean))
				dataBean.setErrorCode(ErrorCodes.CODE_007);
			else
				dataBean.setErrorCode(ErrorCodes.CODE_400);
		}

		dataBean.setType("carSetUp");
		return dataBean;
	}

}
