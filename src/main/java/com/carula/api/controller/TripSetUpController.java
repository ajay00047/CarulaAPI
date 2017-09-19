package com.carula.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carula.api.beans.CarSetUpRequestBean;
import com.carula.api.beans.GenericResponseBean;
import com.carula.api.beans.TripSetUpRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.processor.CarSetUpProcessor;
import com.carula.api.processor.TripSetUpProcessor;
import com.carula.api.validator.CarSetUpValidator;
import com.carula.api.validator.TripSetUpValidator;

@RestController
public class TripSetUpController extends BaseController {
	
	@Autowired
	private TripSetUpValidator tripSetUpValidator;
	@Autowired
	private TripSetUpProcessor tripSetUpProcessor;

	@RequestMapping(value = Constants.tripSetUpUrl,method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody TripSetUpRequestBean requestBean) throws Exception {

		return executeRequest(requestBean,tripSetUpValidator,tripSetUpProcessor);
		
	}

}
