package com.desire3d.event;

public class TokenGeneratedEvent implements IntegrationEvent {

	private static final long serialVersionUID = -1752510264399563813L;

	private final String token;

	private final Long tokenExpiry;

	private final String personUUID;

	public TokenGeneratedEvent(String token, Long tokenExpiry, String personUUID) {
		super();
		this.token = token;
		this.tokenExpiry = tokenExpiry;
		this.personUUID = personUUID;
	}

	public String getToken() {
		return token;
	}

	public long getTokenExpiry() {
		return tokenExpiry;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	@Override
	public String toString() {
		return "TokenGeneratedEvent [token=" + token + ", tokenExpiry=" + tokenExpiry + ", personUUID=" + personUUID + "]";
	}

}
