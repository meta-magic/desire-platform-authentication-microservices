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

	private Integer statusCode;

	public ResponseBean() {
		super();
	}

	public ResponseBean(boolean success, String errorMessage, String errorCode, Integer statusCode) {
		super();
		this.success = success;
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.statusCode = statusCode;
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

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "ResponseBean [success=" + success + ", successMessage=" + successMessage + ", successCode=" + successCode + ", errorMessage=" + errorMessage
				+ ", errorCode=" + errorCode + ", response=" + response + ", statusCode=" + statusCode + "]";
	}

}
