package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "authschema", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class AuthSchema implements Serializable, CommonValidator {

	private static final long serialVersionUID = -4040722821357087375L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String loginUUID;

	@Persistent
	@NotNull(message = "mteid should not be null")
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String mteid;

	@Persistent
	@Unique
	@NotNull(message = "LoginId id should not be null")
	@Size(min = 4, max = 128, message = "Login id must be between 4 and 128 characters")
	private String loginId;

	@Persistent
	@ForeignKey
	@NotNull(message = "userUUID should not be null")
	@Size(min = 1, max = 128, message = "userUUID must be between 1 and 128 characters")
	private String userUUID;

	@Persistent
	@NotNull(message = "personUUID should not be null")
	@Size(min = 1, max = 128, message = "personUUID must be between 1 and 128 characters")
	private String personUUID;

	@Persistent
	@NotNull(message = "isActive should not be null")
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
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