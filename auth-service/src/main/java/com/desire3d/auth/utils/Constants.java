package com.desire3d.auth.utils;

/**
 * Interface to define constants useful in Data Models
 * @author Mahesh Pardeshi
 *
 */
public interface Constants {

	public final static String TEMPLATE_ID_FOR_LOGINID_NOTIFICATION = "a5cbc718-c650-440d-a23d-f2185b80f431";

	public final static String TEMPLATE_ID_FOR_PASSWORD_NOTIFICATION = "b5cbc718-c650-440d-a23d-f2185b80f432";

	public final static Integer DESKTOP_AGENT = 1;

	public final static Integer MOBILE_AGENT = 2;

	public final static Integer TABLET_AGENT = 3;

	public final static Integer SMARTWATCH_AGENT = 4;

	public final static Integer OTHER_AGENT = 5;
	
	public final static Integer PASSWORDHISTORY_LIMIT = 3;

	public final static Long PASSWORD_RECOVERY_TOKEN_EXPIRY = 600000L;

}
