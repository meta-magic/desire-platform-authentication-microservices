package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@PersistenceCapable(table = "passwordschema", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class PasswordSchema implements Serializable, CommonValidator {

	private static final long serialVersionUID = -7739087715200643219L;

	@PrimaryKey
	@Persistent(customValueStrategy = "uuid")
	private String passwordUUID;

	@Persistent
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String mteid;

	@Persistent
	@ForeignKey
	@NotNull(message = "userUUID should not be null")
	@Size(min = 1, max = 128, message = "userUUID must be between 1 and 128 characters")
	private String userUUID;

	@Persistent
	@NotNull(message = "PasswordHash should not be null")
	@Size(min = 1, max = 128, message = "PasswordHash must be between 1 and 128 characters")
	private String passwordHash;

	@Persistent
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	@JsonIgnore
	private AuditDetails auditDetails;

	public PasswordSchema() {

	}

	public PasswordSchema(String mteid, String userUUID, String passwordHash) {
		super();
		this.mteid = mteid;
		this.userUUID = userUUID;
		this.passwordHash = passwordHash;
	}

	public String getPasswordUUID() {
		return passwordUUID;
	}

	public void setPasswordUUID(String passwordUUID) {
		this.passwordUUID = passwordUUID;
	}

	public String getMteid() {
		return mteid;
	}

	public void setMteid(String mteid) {
		this.mteid = mteid;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

	/** method to set audit fields for update operation for own entity */
	public PasswordSchema updateAuditFields(final AuditDetails auditDetails) {
		this.getAuditDetails().setUpdatedBy(auditDetails.getUpdatedBy());
		this.getAuditDetails().setUpdatedTime(auditDetails.getUpdatedTime());
		return this;
	}
}