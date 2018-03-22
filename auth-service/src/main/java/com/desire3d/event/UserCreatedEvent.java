package com.desire3d.event;

/**
 * @author Mahesh Pardeshi
 *
 */
public final class UserCreatedEvent implements IntegrationEvent {

	private static final long serialVersionUID = 7359908991073324430L;

	private String personUUID;

	private String loginId;

	private String firstName;

	private String lastName;

	private String emailId;

	private String phoneNumber;

	private String customerId;

	private String mteid;

	private String orgName;
	
	public UserCreatedEvent() {
		super();
	}

	public UserCreatedEvent(final String personUUID, final String loginId, final String firstName, final String lastName, final String emailId,
			final String phoneNumber, final String customerId, final String mteid, final String orgName) {
		super();
		this.personUUID = personUUID;
		this.loginId = loginId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
		this.customerId = customerId;
		this.mteid = mteid;
		this.orgName = orgName;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	public String getLoginId() {
		return loginId;
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

	public String getCustomerId() {
		return customerId;
	}

	public String getMteid() {
		return mteid;
	}

	public String getOrgName() {
		return orgName;
	}
}