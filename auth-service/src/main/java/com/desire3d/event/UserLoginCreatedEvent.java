package com.desire3d.event;

import com.desire3d.auth.utils.SubscriptionType;

/**
 * Event to represent user login created
 * 
 * @author Mahesh Pardeshi
 *
 */
public final class UserLoginCreatedEvent implements IntegrationEvent {

	private static final long serialVersionUID = 7261464017458701410L;

	private final String userId;
	
	private final String personUUID;

	private final String firstName;

	private final String lastName;

	private final String customerId;

	private final String mteid;

	private final String orgName;
	
	private final SubscriptionType subscriptionType;

	public UserLoginCreatedEvent() {
		super();
		userId = personUUID = firstName = lastName = customerId = mteid = orgName = null;
		subscriptionType = null;
	}

	/**
	 * @param userId
	 * @param personUUID
	 * @param firstName
	 * @param lastName
	 * @param customerId
	 * @param mteid
	 * @param orgName
	 */
	public UserLoginCreatedEvent(String userId, String personUUID, String firstName, String lastName, String customerId, String mteid, String orgName, SubscriptionType subscriptionType) {
		super();
		this.userId = userId;
		this.personUUID = personUUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerId = customerId;
		this.mteid = mteid;
		this.orgName = orgName;
		this.subscriptionType = subscriptionType;
	}

	public String getUserId() {
		return userId;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}
}