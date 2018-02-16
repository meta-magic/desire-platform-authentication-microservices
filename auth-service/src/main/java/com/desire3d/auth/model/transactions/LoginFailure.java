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
import com.fasterxml.jackson.annotation.JsonIgnore;

@PersistenceCapable(table = "loginfailure", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class LoginFailure implements Serializable, CommonValidator {

	private static final long serialVersionUID = -4187597767701326467L;

	@PrimaryKey
	@Persistent(customValueStrategy = "uuid")
	private String loginFailureUUID;

	@Persistent
	@NotNull(message = "Login Id should not be null")
	@Size(min = 1, max = 128, message = "Login Id must be between 1 and 128 characters")
	private String loginId;

	@Persistent
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String mteid;

	@Persistent
	@NotNull(message = "AppServer SessionId should not be null")
	@Size(min = 1, max = 128, message = "AppServer SessionId must be between 1 and 128 characters")
	private String appServerSessionId;

	@Persistent
	@NotNull(message = "Login errorId should not be null")
	@Size(min = 1, max = 128, message = "Login errorId must be between 1 and 128 characters")
	private String loginErrorId;

	@Persistent
	@NotNull(message = "Ip Address should not be null")
	@Size(min = 1, max = 128, message = "Ip Address must be between 1 and 128 characters")
	private String ipAddress;

	@Persistent
	@NotNull(message = "Browser should not be null")
	@Size(min = 1, max = 128, message = "Browser must be between 1 and 128 characters")
	private String browser;

	@Persistent
	@NotNull(message = "User Agent should not be null")
	@Size(min = 1, max = 128, message = "User Agent must be between 1 and 128 characters")
	private String userAgent;

	@Persistent
	@NotNull(message = "Latitude should not be null")
	private Double latitude;

	@Persistent
	@NotNull(message = "Longitude should not be null")
	private Double longitude;

	@Persistent
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	@JsonIgnore
	private AuditDetails auditDetails;

	public LoginFailure() {

	}

	public LoginFailure(String loginId, String mteid, String appServerSessionId, String loginErrorId, String ipAddress, String browser, String userAgent,
			Double latitude, Double longitude) {
		super();

		this.loginId = loginId;
		this.mteid = mteid;
		this.appServerSessionId = appServerSessionId;
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

	public String getAppServerSessionId() {
		return appServerSessionId;
	}

	public void setAppServerSessionId(String appServerSessionId) {
		this.appServerSessionId = appServerSessionId;
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