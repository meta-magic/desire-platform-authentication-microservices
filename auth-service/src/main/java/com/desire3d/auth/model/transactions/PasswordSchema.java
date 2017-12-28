package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "passwordschema")
public class PasswordSchema implements Serializable, CommonValidator {

	private static final long serialVersionUID = -7739087715200643219L;

	@PrimaryKey
	@Persistent(customValueStrategy = "uuid")
	private String passwordUUID;

	@Persistent
	@NotNull
	private String mteid;

	@Persistent
	@ForeignKey
	@NotNull
	private String userUUID;

	@Persistent
	@NotNull
	private String passwordHash;

	@Persistent
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")), @Persistent(name = "createdTime", columns = @Column(name = "createdTime")),
			@Persistent(name = "updatedBy", columns = @Column(name = "updatedBy")),
			@Persistent(name = "updatedTime", columns = @Column(name = "updatedTime")) })
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
}