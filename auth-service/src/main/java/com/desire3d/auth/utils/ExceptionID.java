package com.desire3d.auth.utils;

public interface ExceptionID {

	/** SUCCESS MESSAGE ID'S */
	public final String SUCCESS_PERSISTENCE = "success.persistence";
	public final String SUCCESS_RETRIEVE = "success.retrieve";

	/** TOKEN RELATED ID'S */
	public final String TOKEN_REQUIRED = "token.required";
	public final String TOKEN_EXPIRED = "token.expired";
	public final String TOKEN_INVALID = "token.invalid";

	/** ERROR MESSAGE ID'S */
	public final String ERROR_GLOBAL = "error.global";
	public final String ERROR_PERSISTENCE = "error.persistence";
	public final String ERROR_RETRIEVE = "error.retrieve";
	public final String ERROR_VALIDATION = "error.validation";

}