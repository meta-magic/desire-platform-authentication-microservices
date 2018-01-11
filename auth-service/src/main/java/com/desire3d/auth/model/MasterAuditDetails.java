package com.desire3d.auth.model;

import java.util.Date;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;

/**
 * @author rashmi
 *
 */
@PersistenceCapable(detachable = "true")
@EmbeddedOnly
public class MasterAuditDetails {

	private String createdBy;

	private Date createdTime;

	public MasterAuditDetails() {
		super();
	}

	public MasterAuditDetails(String createdBy, Date createdTime) {
		super();
		this.createdBy = createdBy;
		this.createdTime = createdTime;
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
}