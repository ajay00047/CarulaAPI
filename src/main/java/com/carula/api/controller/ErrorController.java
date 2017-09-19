package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carula.api.beans.GenericResponseBean;
import com.carula.api.beans.LoginRequestBean;
import com.carula.api.constants.Constants;

@RestController
public class ErrorController extends BaseController {
	
	@RequestMapping(value = Constants.errorUrl, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody LoginRequestBean requestBean) throws Exception {

		throw new Exception();
		
	}

}
