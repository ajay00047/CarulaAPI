package com.carula.api.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.beans.CarDetailsResponseBean;
import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.constants.ErrorCodes;
import com.carula.api.dao.UserDAO;
import com.carula.api.delegate.DataBean;
import com.carula.api.delegate.Processor;

@Service
public class CarDetailsProcessor implements Processor {

	@Autowired
	@Qualifier("carDetailsResponseBean")
	private CarDetailsResponseBean dataBean;
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public DataBean process(BaseRequestBean theRequestBean) throws Exception{
		BaseRequestBean requestBean = (BaseRequestBean) theRequestBean;
		
		int userId = userDAO.getUserIdFromPassKey(requestBean.getPassKey());
				
		if(userId>0){
			CarSetUpRequestBean bean = userDAO.getCar(userId);
			if(bean != null){
				dataBean.setErrorCode(ErrorCodes.CODE_201);
				dataBean.setCompany(bean.getCompany());
				dataBean.setModel(bean.getModel());
				dataBean.setColor(bean.getColor());
				dataBean.setNo(bean.getNo());
			}
			else{
				dataBean.setErrorCode(ErrorCodes.CODE_202);
			}
		}

		dataBean.setType("carDetails");
		return dataBean;
	}

}
