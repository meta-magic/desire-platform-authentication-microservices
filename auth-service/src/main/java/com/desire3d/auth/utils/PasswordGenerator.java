package com.desire3d.auth.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * User password generation class to generate password using Apache commons library 
 * 
 * @author Mahesh Pardeshi
 *
 */
public final class PasswordGenerator {

	private final static Integer DEFAULT_PASSWORD_LENGTH = 8;

	private PasswordGenerator() {
	}
	
	/**
	 * Generating alphanumeric password of DEFAULT_PASSWORD_LENGTH 8 
	 * */
	public static String generatePassword() {
		return generatePassword(DEFAULT_PASSWORD_LENGTH);
	}
	
	/**
	 * Generating alphanumeric password of user defined length
	 * */
	public static String generatePassword(final Integer passwordLength) {
		return RandomStringUtils.randomAlphanumeric(passwordLength);
	}
}
