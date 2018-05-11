package com.desire3d.event;

/**
 * Event to perform post logout activities
 * 
 * */
public class UserLoggedoutEvent implements IntegrationEvent {

	private static final long serialVersionUID = 4902173808330704728L;

	private String personUUID;

	public UserLoggedoutEvent() {
		super();
	}

	/**
	 * @param personUUID
	 */
	public UserLoggedoutEvent(String personUUID) {
		super();
		this.personUUID = personUUID;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	@Override
	public String toString() {
		return "UserLoggedoutEvent [personUUID=" + personUUID + "]";
	}
}