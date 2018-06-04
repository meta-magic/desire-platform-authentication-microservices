package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@PersistenceCapable(table = "recoverytoken", detachable = "true")
public class RecoveryToken implements Serializable, CommonValidator {

	private static final long serialVersionUID = -5975519542591965696L;

	@Persistent
	@NotNull(message = "tokenId should not be null")
	@Size(min = 1, max = 255, message = "tokenId must be between 1 and 255 characters")
	@PrimaryKey
	private String tokenId;

	@Persistent
	@NotNull(message = "token should not be null")
	@Size(min = 1, max = 1024, message = "token must be between 1 and 1024 characters")
	private String token;

	@Persistent
	@NotNull(message = "token expiry should not be null")
	private Long tokenExpiry;

	@Persistent
	@NotNull(message = "personId should not be null")
	@Size(min = 1, max = 255, message = "personId must be between 1 and 255 characters")
	private String personId;

	@Persistent(defaultFetchGroup = "true")
	@JsonIgnore
	private AuditDetails auditDetails;

	public RecoveryToken() {
		super();
	}

	public RecoveryToken(String tokenId, String token, Long tokenExpiry, String personId) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.tokenExpiry = tokenExpiry;
		this.personId = personId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public String getToken() {
		return token;
	}

	public Long getTokenExpiry() {
		return tokenExpiry;
	}

	public String getPersonId() {
		return personId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTokenExpiry(Long tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

	@Override
	public String toString() {
		return "RecoveryToken [tokenId=" + tokenId + ", token=" + token + ", tokenExpiry=" + tokenExpiry + ", personId="
				+ personId + "]";
	}
}