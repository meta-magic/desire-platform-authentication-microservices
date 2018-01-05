package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "loginhistory")
public class LoginHistory implements Serializable, CommonValidator {

	private static final long serialVersionUID = 734183511634030663L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String loginHistoryUUID;

	@Persistent
	@NotNull(message = "mteid should not be null")
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String mteid;

	@Persistent
	@NotNull(message = "userUUID should not be null")
	@Size(min = 1, max = 128, message = "userUUID must be between 1 and 128 characters")
	private String userUUID;

	@Persistent
	@NotNull(message = "AppSessionId should not be null")
	@Size(min = 1, max = 128, message = "AppSessionId must be between 1 and 128 characters")
	private String appSessionId;

	@Persistent
	@ForeignKey
	@NotNull(message = "Login Type should not be null")
	private Integer loginTypeId;

	@Persistent
	@ForeignKey
	@NotNull(message = "Login Form Factor should not be null")
	private Integer loginFormFactorId;

	@Persistent
	@NotNull(message = "mteid should not be null")
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String ipAddress;

	@Persistent
	@NotNull(message = "Ip Address should not be null")
	@Size(min = 1, max = 128, message = "Ip Address must be between 1 and 128 characters")
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
	@NotNull(message = "isActive Status should not be null")
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")), @Persistent(name = "createdTime", columns = @Column(name = "createdTime")),
			@Persistent(name = "updatedBy", columns = @Column(name = "updatedBy")),
			@Persistent(name = "updatedTime", columns = @Column(name = "updatedTime")) })
	private AuditDetails auditDetails;

	public LoginHistory() {

	}

	public LoginHistory(String mteid, String userUUID, String appSessionId, Integer loginTypeId, Integer loginFormFactorId, String ipAddress, String browser,
			String userAgent, Double latitude, Double longitude) {
		super();
		this.mteid = mteid;
		this.userUUID = userUUID;
		this.appSessionId = appSessionId;
		this.loginTypeId = loginTypeId;
		this.loginFormFactorId = loginFormFactorId;
		this.ipAddress = ipAddress;
		this.browser = browser;
		this.userAgent = userAgent;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLoginHistoryUUID() {
		return loginHistoryUUID;
	}

	public void setLoginHistoryUUID(String loginHistoryUUID) {
		this.loginHistoryUUID = loginHistoryUUID;
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

	public String getAppSessionId() {
		return appSessionId;
	}

	public void setAppSessionId(String appSessionId) {
		this.appSessionId = appSessionId;
	}

	public Integer getLoginTypeId() {
		return loginTypeId;
	}

	public void setLoginTypeId(Integer loginTypeId) {
		this.loginTypeId = loginTypeId;
	}

	public Integer getLoginFormFactorId() {
		return loginFormFactorId;
	}

	public void setLoginFormFactorId(Integer loginFormFactorId) {
		this.loginFormFactorId = loginFormFactorId;
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

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}
}