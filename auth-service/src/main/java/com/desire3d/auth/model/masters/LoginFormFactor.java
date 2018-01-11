package com.desire3d.auth.model.masters;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.desire3d.auth.model.MasterAuditDetails;
import com.desire3d.auth.utils.CommonValidator;

@PersistenceCapable(table = "loginformfactor", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class LoginFormFactor implements Serializable, CommonValidator {

	private static final long serialVersionUID = 1L;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	@PrimaryKey
	private Integer loginFormFactorId;

	@Persistent
	@NotNull(message = "Login factor label should not be null")
	@Size(min = 1, max = 128, message = "Login factor label must be between 1 and 128 characters")
	private String label;

	@Persistent
	@NotNull(message = "Login factor icon should not be null")
	@Size(min = 1, max = 128, message = "Login factor icon must be between 1 and 128 characters")
	private String icon;

	@Persistent
	@NotNull(message = "Login factor help should not be null")
	@Size(min = 1, max = 256, message = "Login factor help must be between 1 and 256 characters")
	private String help;

	@Persistent
	@NotNull(message = "Login factor description should not be null")
	@Size(min = 1, max = 128, message = "Login factor description must be between 1 and 128 characters")
	private String description;

	@Persistent
	@NotNull(message = "isActive Status should not be null")
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	private MasterAuditDetails auditDetails;

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

	public MasterAuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(MasterAuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

}