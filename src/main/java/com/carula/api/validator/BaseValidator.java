package com.carula.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.carula.api.beans.BaseRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.dao.UserDAO;

@SuppressWarnings("rawtypes")
@Component
public class BaseValidator implements Validator {

	@Autowired
	private UserDAO userDAO;
	
	private boolean passKeyValidation;

	public BaseValidator() {
		this.passKeyValidation = false;
	}

	@Autowired
	public BaseValidator(boolean passKeyValidation) {
		this.passKeyValidation = passKeyValidation;
	}

	public boolean supports(Class clazz) {
		return BaseRequestBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BaseRequestBean bean = (BaseRequestBean) target;
		if (null == bean.getAppPassCode() || bean.getAppPassCode().equals("")) {
			errors.reject("Invalid AppPassCode");
		}

		// check pass key
		if (passKeyValidation) {
			if (null == bean.getPassKey() || bean.getPassKey().equals("") || !validPassKey(bean.getPassKey()))
				errors.reject(Constants.INVALID_PASSKEY);

		}
	}

	public boolean validPassKey(String passKey) {
		if(userDAO.getUserIdFromPassKey(passKey) == 0)
			return false;
		else
			return true;
	}

}
