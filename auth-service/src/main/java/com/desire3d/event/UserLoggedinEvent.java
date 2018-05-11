package com.desire3d.event;

/**
 * Event to perform post login activities
 * 
 * */
public class UserLoggedinEvent implements IntegrationEvent {

	private static final long serialVersionUID = 4902173808330704728L;

	private String personUUID;

	public UserLoggedinEvent() {
		super();
	}

	/**
	 * @param personUUID
	 */
	public UserLoggedinEvent(String personUUID) {
		super();
		this.personUUID = personUUID;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	@Override
	public String toString() {
		return "UserLoggedinEvent [personUUID=" + personUUID + "]";
	}
}