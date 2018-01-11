package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "appsession", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class AppSession implements Serializable, CommonValidator {

	private static final long serialVersionUID = 3293773678508530179L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String appSessionId;

	@Persistent
	private String appData;

	@Persistent
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	private AuditDetails auditDetails;

	public AppSession() {

	}

	public AppSession(String appSessionId, String appData) {
		super();
		this.appSessionId = appSessionId;
		this.appData = appData;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getAppSessionId() {
		return appSessionId;
	}

	public void setAppSessionId(String appSessionId) {
		this.appSessionId = appSessionId;
	}

	public String getAppData() {
		return appData;
	}

	public void setAppData(String appData) {
		this.appData = appData;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}
}