package com.desire3d.auth.model;

import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@PersistenceCapable
@EmbeddedOnly
@Version(column = "version", strategy = VersionStrategy.VERSION_NUMBER)
public class AuditDetails {

	private Integer version;

	private String createdBy;

	private Date createdTime;

	private String updatedBy;

	private Date updatedTime;

	public AuditDetails() {
		super();
	}

	/** Constructor to create {@link AuditDetails} to audit update log */
	public AuditDetails(String updatedBy, Date updatedTime) {
		super();
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	/** Constructor to create {@link AuditDetails} to audit save log */
	public AuditDetails(String createdBy, Date createdTime, String updatedBy, Date updatedTime) {
		super();
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}