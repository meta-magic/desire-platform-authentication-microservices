package com.desire3d.auth.model.masters;

import java.io.Serializable;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.validation.constraints.NotNull;

import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "loginformfactor")
public class LoginFormFactor implements Serializable, CommonValidator {

	private static final long serialVersionUID = 1L;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	@PrimaryKey
	private Integer loginFormFactorId;

	@Persistent
	@NotNull
	private String label;

	@Persistent
	private String icon;

	@Persistent
	private String help;

	@Persistent
	private String description;

	@Persistent
	@NotNull
	private Boolean isActive = true;

	@Embedded(members = { @Persistent(name = "version", columns = @Column(name = "version")),
			@Persistent(name = "createdBy", columns = @Column(name = "createdBy")),
			@Persistent(name = "createdTime", columns = @Column(name = "createdTime")) })
	private AuditDetails auditDetails;

	public LoginFormFactor() {

	}

	public LoginFormFactor(String label, String icon, String help, String description) {
		super();
		this.label = label;
		this.icon = icon;
		this.help = help;
		this.description = description;
	}

	public Integer getLoginFormFactorId() {
		return loginFormFactorId;
	}

	public void setLoginFormFactorId(Integer loginFormFactorId) {
		this.loginFormFactorId = loginFormFactorId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(AuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}
}