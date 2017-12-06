package com.desire3d.auth.beans;

/**
 * @author ketangote
 * 
 * Response bean
 */
import java.io.Serializable;

public class ResponseBean implements Serializable {
	private boolean success;

	private String successMessage;

	private String successCode;

	private String errorMessage;

	private String errorCode;

	private Object response;

	public ResponseBean() {
		super();
	}

	public ResponseBean(boolean success, String successMessage, String successCode, String errorMessage, String errorCode, Object response) {
		super();
		this.success = success;
		this.successMessage = successMessage;
		this.successCode = successCode;
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.response = response;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public String getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "ResponseBean [success=" + success + ", successMessage=" + successMessage + ", successCode=" + successCode + ", errorMessage=" + errorMessage
				+ ", errorCode=" + errorCode + ", response=" + response + "]";
	}

}
