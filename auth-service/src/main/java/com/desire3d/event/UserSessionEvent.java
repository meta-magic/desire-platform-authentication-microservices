package com.desire3d.event;

import java.io.Serializable;

/**
 * @author sagar THIS IS EVENT IS CONSUME WHEN USER IS SESSION TIME OUT
 */
public class UserSessionEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7498582572221199127L;

	private String sessionId;

	private String tokenId;

	private Integer type;

	public UserSessionEvent() {

	}

	public UserSessionEvent(String sessionId, String tokenId, Integer type) {
		super();
		this.sessionId = sessionId;
		this.tokenId = tokenId;
		this.type = type;
	}

	public String getTokenId() {
		return tokenId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Integer getType() {
		return type;
	}

}
