package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carula.api.beans.GenericResponseBean;
import com.carula.api.beans.VerifyOTPRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.processor.VerifyOTPProcessor;
import com.carula.api.validator.VerifyOTPValidator;

@RestController
public class VerifyOTPController extends BaseController {
	
	@Autowired
	private VerifyOTPValidator verifyOTPValidator;
	@Autowired
	private VerifyOTPProcessor verifyOTPProcessor;

	@RequestMapping(value = Constants.verifyOTPUrl,method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody VerifyOTPRequestBean requestBean) throws Exception {

		return executeRequest(requestBean,verifyOTPValidator,verifyOTPProcessor);
		
	}

}
