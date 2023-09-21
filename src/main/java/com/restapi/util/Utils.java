package com.restapi.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public interface Utils {

	public static BigInteger genarateRandomNumber32() {
		SecureRandom secureRandom = new SecureRandom();
		BigInteger randomId = new BigInteger(32, secureRandom);
		return randomId;
	}

}