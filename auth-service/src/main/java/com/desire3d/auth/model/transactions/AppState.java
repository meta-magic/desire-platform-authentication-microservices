package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@PersistenceCapable(table = "appstate", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class AppState implements Serializable, CommonValidator {

	private static final long serialVersionUID = -5588316615133630427L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String appStateUUID;

	@Persistent
	@Size(min = 1, max = 128, message = "mteid must be between 1 and 128 characters")
	private String mteid;

	@Persistent
	@ForeignKey
	//	@NotNull(message = "loginUUID should not be null")
	//	@Size(min = 1, max = 128, message = "loginUUID must be between 1 and 128 characters")
	private String loginUUID;

	@Persistent
	@ForeignKey
	//	@NotNull(message = "appSessionId should not be null")
	//	@Size(min = 1, max = 128, message = "appSessionId must be between 1 and 128 characters")
	private String appSessionId;

	@Persistent
	//	@NotNull(message = "tabId should not be null")
	private Integer tabId;

	@Persistent
	//	@NotNull(message = "tabData should not be null")
	//	@Size(min = 1, max = 10240, message = "tabData must be between 1 and 10240 characters")
	private String tabData;

	@Persistent
	//	@NotNull(message = "isTabActive should not be null")
	private Boolean isTabActive;

	@Persistent
	//	@NotNull(message = "userAgent should not be null")
	//	@Size(min = 1, max = 128, message = "userAgent must be between 1 and 128 characters")
	private String userAgent;

	@Persistent
	//	@NotNull(message = "latitude should not be null")
	private Double latitude;

	@Persistent
	//	@NotNull(message = "longitude should not be null")
	private Double longitude;

	@Persistent
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	@JsonIgnore
	private AuditDetails auditDetails;

	public AppState() {

	}

	public AppState(String mteid, String loginUUID, String appSessionId, Integer tabId, String tabData, Boolean isTabActive, String userAgent, Double latitude,
			Double longitude) {
		super();
		this.mteid = mteid;
		this.loginUUID = loginUUID;
		this.appSessionId = appSessionId;
		this.tabId = tabId;
		this.tabData = tabData;
		this.isTabActive = isTabActive;
		this.userAgent = userAgent;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAppStateUUID() {
		return appStateUUID;
	}

	public void setAppStateUUID(String appStateUUID) {
		this.appStateUUID = appStateUUID;
	}

	public String getMteid() {
		return mteid;
	}

	public void setMteid(String mteid) {
		this.mteid = mteid;
	}

	public String getLoginUUID() {
		return loginUUID;
	}

	public void setLoginUUID(String loginUUID) {
		this.loginUUID = loginUUID;
	}

	public String getAppSessionId() {
		return appSessionId;
	}

	public void setAppSessionId(String appSessionId) {
		this.appSessionId = appSessionId;
	}

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public String getTabData() {
		return tabData;
	}

	public void setTabData(String tabData) {
		this.tabData = tabData;
	}

	public Boolean getIsTabActive() {
		return isTabActive;
	}

	public void setIsTabActive(Boolean isTabActive) {
		this.isTabActive = isTabActive;
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

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}
}