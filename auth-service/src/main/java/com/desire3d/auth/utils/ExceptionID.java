package com.desire3d.auth.utils;

public interface ExceptionID {

	/** SUCCESS MESSAGE ID'S */
	public final String SUCCESS_PERSISTENCE = "success.persistence";
	public final String SUCCESS_RETRIEVE = "success.retrieve";
	public final String VALID_LOGINID = "valid.loginid";
	public final String VALID_USERCREDENTIALS = "valid.user.credentials";
	public final String LOGINID_AVAILABLE = "loginid.available";
	public final String USER_LOGOUT = "user.logout";

	/** TOKEN RELATED ID'S */
	public final String TOKEN_REQUIRED = "token.required";
	public final String TOKEN_EXPIRED = "token.expired";
	public final String TOKEN_INVALID = "token.invalid";

	/** ERROR MESSAGE ID'S */
	public final String ERROR_GLOBAL = "error.global";
	public final String ERROR_PERSISTENCE = "error.persistence";
	public final String ERROR_UPDATE = "error.update";
	public final String ERROR_RETRIEVE = "error.retrieve";
	public final String ERROR_VALIDATION = "error.validation";

	public final String INVALID_LOGINID = "invalid.loginid";
	public final String INVALID_USER_CREDENTIALS = "invalid.user.credentials";
	public final String INVALID_LOGINID_ACCOUNTBLOCKEDOREXPIRED = "invalid.loginid.accountblockedorexpired";
	public final String INVALID_USERSESSION = "invalid.usersession";
	public final String LOGINID_EXISTS = "loginid.exists";
	public final String LOGOUT_FAILED = "logout.failed";

}