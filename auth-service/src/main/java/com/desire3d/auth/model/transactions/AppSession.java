package com.desire3d.auth.model.transactions;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.desire3d.auth.model.AuditDetails;

@PersistenceCapable(table = "appsession")
public class AppSession implements Serializable {

	private static final long serialVersionUID = 3293773678508530179L;

	@Persistent(customValueStrategy = "uuid")
	@PrimaryKey
	private String appSessionId;

	@Persistent
	private String appData;

	@Persistent
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")), @Persistent(name = "createdTime", columns = @Column(name = "createdTime")),
			@Persistent(name = "updatedBy", columns = @Column(name = "updatedBy")),
			@Persistent(name = "updatedTime", columns = @Column(name = "updatedTime")) })
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