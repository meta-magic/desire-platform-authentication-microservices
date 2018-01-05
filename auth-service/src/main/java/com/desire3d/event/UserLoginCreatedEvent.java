package com.desire3d.event;

/**
 * Event to represent user login created
 * 
 * @author Mahesh Pardeshi
 *
 */
public final class UserLoginCreatedEvent implements IntegrationEvent {

	private static final long serialVersionUID = -7261464017458701410L;

	private final String userId;

	public UserLoginCreatedEvent() {
		super();
		this.userId = null;
	}

	public UserLoginCreatedEvent(final String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
}