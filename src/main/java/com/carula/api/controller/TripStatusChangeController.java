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
import com.carula.api.beans.TripStatusChangeRequestBean;
import com.carula.api.constants.Constants;
import com.carula.api.processor.MyTripProcessor;
import com.carula.api.processor.TripStatusChangeProcessor;

@RestController
public class TripStatusChangeController extends BaseController {
	
	@Autowired
	private TripStatusChangeProcessor tripStatusChangeProcessor;

	@RequestMapping(value = Constants.changeTripStatusUrl,method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericResponseBean processRequest(@Valid @RequestBody TripStatusChangeRequestBean requestBean) throws Exception {

		return executeRequest(requestBean,null,tripStatusChangeProcessor);
		
	}

}
