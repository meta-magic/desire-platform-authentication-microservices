package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "userschema")
public class UserSchema implements Serializable, CommonValidator {

	private static final long serialVersionUID = 3874046290081502939L;

	@PrimaryKey
	@Persistent(customValueStrategy = "uuid")
	@Index
	private String userUUID;

	@NotNull
	@Persistent
	private String mteid;

	@Persistent
	@NotNull
	private String userType;

	@Persistent
	@NotNull
	private Boolean firstTimeLogin = false;

	@Persistent
	@NotNull
	private Integer accountBlocked = 0;

	@NotNull
	@Persistent
	private Boolean accountExpired = false;

	@Persistent
	private Boolean changePassword = false;

	@Index
	@Persistent
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")), @Persistent(name = "createdTime", columns = @Column(name = "createdTime")),
			@Persistent(name = "updatedBy", columns = @Column(name = "updatedBy")),
			@Persistent(name = "updatedTime", columns = @Column(name = "updatedTime")) })
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