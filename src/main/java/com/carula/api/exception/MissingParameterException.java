package com.carula.api.exception;

import com.carula.api.constants.Constants;

public class MissingParameterException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingParameterException(){
		super(Constants.VALIDATION_ERROR+"[Missing Parameter]");
	}

}
