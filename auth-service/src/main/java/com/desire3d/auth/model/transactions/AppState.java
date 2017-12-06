package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.desire3d.auth.model.AuditDetails;

@PersistenceCapable(table = "appstate")
public class AppState implements Serializable {

	private static final long serialVersionUID = -5588316615133630427L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String appStateUUID;

	@Persistent
	private String mteid;

	@Persistent
	private String loginUUID;

	@Persistent
	private String appSessionId;

	@Persistent
	private Integer tabId;

	@Persistent
	private String tabData;

	@Persistent
	private Boolean isTabActive;

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