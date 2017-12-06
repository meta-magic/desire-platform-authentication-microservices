package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;

@PersistenceCapable(table = "authschema")
public class AuthSchema implements Serializable {

	private static final long serialVersionUID = -4040722821357087375L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String loginUUID;

	@Persistent
	private String mteid;

	@Size(min = 1, max = 50, message = "loginId should not be greater than 50 characters")
	@Persistent
	private String loginId;

	@ForeignKey
	@Persistent
	private String userUUID;

	@Persistent
	// @ForeignKey
	private String personUUID;

	@Persistent
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")), @Persistent(name = "createdTime", columns = @Column(name = "createdTime")),
			@Persistent(name = "updatedBy", columns = @Column(name = "updatedBy")),
			@Persistent(name = "updatedTime", columns = @Column(name = "updatedTime")) })
	private AuditDetails auditDetails;

	public AuthSchema() {

	}

	public AuthSchema(String mteid, String loginId, String userUUID, String personUUID) {
		super();
		this.mteid = mteid;
		this.loginId = loginId;
		this.userUUID = userUUID;
		this.personUUID = personUUID;
	}

	public String getLoginUUID() {
		return loginUUID;
	}

	public void setLoginUUID(String loginUUID) {
		this.loginUUID = loginUUID;
	}

	public String getMteid() {
		return mteid;
	}

	public void setMteid(String mteid) {
		this.mteid = mteid;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	public void setPersonUUID(String personUUID) {
		this.personUUID = personUUID;
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