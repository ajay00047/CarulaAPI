package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carula.api.beans.GenericResponseBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.processor.GetTripProcessor;
import com.carula.api.validator.GetTripValidator;

@RestController
public class GetTripController extends BaseController {
	
	@Autowired
	private GetTripValidator getTripValidator;
	@Autowired
	private GetTripProcessor getTripProcessor;

	@RequestMapping(value = Constants.getTripUrl,method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody TripSetUpRequestBean requestBean) throws Exception {

		return executeRequest(requestBean,getTripValidator,getTripProcessor);
		
	}

}
