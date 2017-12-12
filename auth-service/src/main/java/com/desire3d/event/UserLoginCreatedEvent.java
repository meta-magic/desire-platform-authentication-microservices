package com.desire3d.event;

/**
 * @author Mahesh Pardeshi
 *
 */
public final class UserLoginCreatedEvent implements IntegrationEvent {

	private static final long serialVersionUID = -7261464017458701410L;

	private String loginId;

	private String userId;

	private String password;

	private String firstName;

	private String lastName;

	private String emailId;

	private String phoneNumber;

	public UserLoginCreatedEvent() {
		super();
	}

	public UserLoginCreatedEvent(final String loginId, final String userId, final String password, final String firstName, final String lastName,
			final String emailId, final String phoneNumber) {
		super();
		this.loginId = loginId;
		this.userId = userId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public String toString() {
		return "UserLoginCreatedEvent [loginId=" + loginId + ", userId=" + userId + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", emailId=" + emailId + ", phoneNumber=" + phoneNumber + "]";
	}
}