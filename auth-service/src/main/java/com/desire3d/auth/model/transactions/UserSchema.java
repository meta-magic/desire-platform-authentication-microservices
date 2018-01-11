package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "userschema", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class UserSchema implements Serializable, CommonValidator {

	private static final long serialVersionUID = 3874046290081502939L;

	@PrimaryKey
	@Persistent(customValueStrategy = "uuid")
	private String userUUID;

	@Persistent
	@NotNull(message = "mteid should not be null")
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String mteid;

	@Persistent
	@NotNull(message = "User Type should not be null")
	@Size(min = 1, max = 128, message = "User Type must be between 1 and 128 characters")
	private String userType;

	@Persistent
	@NotNull(message = "First Time Login should not be null")
	private Boolean firstTimeLogin = false;

	@Persistent
	@NotNull(message = "Account Blocked should not be null")
	private Integer accountBlocked = 0;

	@Persistent
	@NotNull(message = "Account Expired should not be null")
	private Boolean accountExpired = false;

	@Persistent
	@NotNull(message = "Change Password should not be null")
	private Boolean changePassword = false;

	@Persistent
	@NotNull(message = "isActive Status should not be null")
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	private AuditDetails auditDetails;

	public UserSchema() {

	}

	public UserSchema(String mteid, String userType, Boolean firstTimeLogin, Integer accountBlocked, Boolean accountExpired, Boolean changePassword) {
		super();
		this.mteid = mteid;
		this.userType = userType;
		this.firstTimeLogin = firstTimeLogin;
		this.accountBlocked = accountBlocked;
		this.accountExpired = accountExpired;
		this.changePassword = changePassword;
	}

	public String getUserUUID() {
		return userUUID;
	}

	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	public String getMteid() {
		return mteid;
	}

	public void setMteid(String mteid) {
		this.mteid = mteid;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Boolean isFirstTimeLogin() {
		return firstTimeLogin;
	}

	public void setFirstTimeLogin(Boolean firstTimeLogin) {
		this.firstTimeLogin = firstTimeLogin;
	}

	public Integer getAccountBlocked() {
		return accountBlocked;
	}

	public void setAccountBlocked(Integer accountBlocked) {
		this.accountBlocked = accountBlocked;
	}

	public Boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Boolean isChangePassword() {
		return changePassword;
	}

	public void setChangePassword(Boolean changePassword) {
		this.changePassword = changePassword;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getFirstTimeLogin() {
		return firstTimeLogin;
	}

	public Boolean getAccountExpired() {
		return accountExpired;
	}

	public Boolean getChangePassword() {
		return changePassword;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}
}