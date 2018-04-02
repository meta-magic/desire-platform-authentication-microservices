package com.desire3d.auth.beans;

/**
 * Response bean
 */
import java.io.Serializable;
import java.util.List;

public class ResponseBean implements Serializable {

	private static final long serialVersionUID = 4406922404046365093L;

	private boolean success;

	private String successMessage;

	private String successCode;

	private String errorCode;

	private String errorMessage;

	private List<String> errors;

	private Object response;

	public ResponseBean() {
		super();
	}

	/**
	 * Constructor to build success response
	 * 
	 * e.g ResponseBean response = new ResponseBean(true, "completed operation
	 * successfully", "success.persistence", responseData);
	 * 
	 */
	public ResponseBean(boolean success, String successMessage, String successCode, Object response) {
		super();
		this.success = success;
		this.successMessage = successMessage;
		this.successCode = successCode;
		this.response = response;
	}

	/**
	 * Constructor to build exceptional response
	 * 
	 * e.g ResponseBean responseBean = new ResponseBean(false, "error.persistence")
	 * 
	 */
	public ResponseBean(boolean success, String errorCode) {
		super();
		this.success = success;
		this.errorCode = errorCode;
	}
	
	/**
	 * Constructor to build exceptional response
	 * 
	 * e.g ResponseBean responseBean = new ResponseBean(false, error.persistence,
	 * "Data failed to save", Arrays.asList(exception.getMessage()));
	 * 
	 */
	public ResponseBean(boolean success, String errorMessage, String errorCode) {
		super();
		this.success = success;
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
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
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
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

	public List<String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ResponseBean [success=" + success + ", successMessage=" + successMessage + ", successCode="
				+ successCode + ", errorMessage=" + errorMessage + ", errorCode=" + errorCode + ", response=" + response
				+ "]";
	}
}
