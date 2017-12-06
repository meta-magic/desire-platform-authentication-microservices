package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.desire3d.auth.model.AuditDetails;

@PersistenceCapable(table = "loginfailure")
public class LoginFailure implements Serializable {

	private static final long serialVersionUID = -4187597767701326467L;

	@PrimaryKey
	@Persistent(customValueStrategy = "uuid")
	private String loginFailureUUID;

	@Persistent
	private String loginId;

	@Persistent
	private String mteid;

	@Persistent
	private String appSessionId;

	@Persistent
	private String loginErrorId;

	@Persistent
	private String ipAddress;

	@Persistent
	private String browser;

	@Persistent
	private String userAgent;

	@Persistent
	private Double latitude;

	@Persistent
	private Double longitude;

	@Persistent
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")), @Persistent(name = "createdTime", columns = @Column(name = "createdTime")),
			@Persistent(name = "updatedBy", columns = @Column(name = "updatedBy")),
			@Persistent(name = "updatedTime", columns = @Column(name = "updatedTime")) })
	private AuditDetails auditDetails;

	public LoginFailure() {

	}

	public LoginFailure(String loginId, String mteid, String appSessionId, String loginErrorId, String ipAddress, String browser, String userAgent,
			Double latitude, Double longitude) {
		super();

		this.loginId = loginId;
		this.mteid = mteid;
		this.appSessionId = appSessionId;
		this.loginErrorId = loginErrorId;
		this.ipAddress = ipAddress;
		this.browser = browser;
		this.userAgent = userAgent;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLoginFailureUUID() {
		return loginFailureUUID;
	}

	public void setLoginFailureUUID(String loginFailureUUID) {
		this.loginFailureUUID = loginFailureUUID;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAppSessionId() {
		return appSessionId;
	}

	public void setAppSessionId(String appSessionId) {
		this.appSessionId = appSessionId;
	}

	public String getLoginErrorId() {
		return loginErrorId;
	}

	public void setLoginErrorId(String loginErrorId) {
		this.loginErrorId = loginErrorId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getMteid() {
		return mteid;
	}

	public void setMteid(String mteid) {
		this.mteid = mteid;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}
}