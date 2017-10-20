package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carula.api.beans.GenericResponseBean;
import com.carula.api.beans.MyTripsRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.processor.TripRequestsProcessor;

@RestController
public class TripRequestsController extends BaseController {
	
	@Autowired
	private TripRequestsProcessor tripRequestsProcessor;

	@RequestMapping(value = Constants.tripRequestsUrl,method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody MyTripsRequestBean requestBean) throws Exception {

		return executeRequest(requestBean,null,tripRequestsProcessor);
		
	}

}
