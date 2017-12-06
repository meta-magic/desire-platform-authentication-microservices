package com.desire3d.auth.dto;

import java.io.Serializable;

import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.model.transactions.UserSchema;

public class AuthenticateResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuthSchema authSchema;

	private UserSchema userSchema;

	private PasswordSchema passwordSchema;

	public AuthenticateResponse(AuthSchema auth, UserSchema user, PasswordSchema passwordSchema2) {
		super();
		this.authSchema = auth;
		this.userSchema = user;
		this.passwordSchema = passwordSchema2;
	}

	public AuthSchema getAuthSchema() {
		return authSchema;
	}

	public void setAuthSchema(AuthSchema authSchema) {
		this.authSchema = authSchema;
	}

	public UserSchema getUserSchema() {
		return userSchema;
	}

	public void setUserSchema(UserSchema userSchema) {
		this.userSchema = userSchema;
	}

	public PasswordSchema getPasswordSchema() {
		return passwordSchema;
	}

	public void setPasswordSchema(PasswordSchema passwordSchema) {
		this.passwordSchema = passwordSchema;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
