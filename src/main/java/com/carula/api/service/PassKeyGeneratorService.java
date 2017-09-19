package com.carula.api.service;

import com.carula.api.util.Utils;

public final class PassKeyGeneratorService {
	public static String generatepassKey(String mobile) throws Exception {

		return Utils.generateRandomString(16);
		
	}
}
