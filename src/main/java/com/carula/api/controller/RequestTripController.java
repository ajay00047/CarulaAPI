package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carula.api.beans.GenericResponseBean;
import com.carula.api.beans.RequestTripBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.processor.GetTripProcessor;
import com.carula.api.processor.RequestTripProcessor;
import com.carula.api.validator.GetTripValidator;
import com.carula.api.validator.RequestTripValidator;

@RestController
public class RequestTripController extends BaseController {
	
	@Autowired
	private RequestTripValidator requestTripValidator;
	@Autowired
	private RequestTripProcessor requestTripProcessor;

	@RequestMapping(value = Constants.requestTripUrl,method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody RequestTripBean requestBean) throws Exception {

		return executeRequest(requestBean,requestTripValidator,requestTripProcessor);
		
	}

}
