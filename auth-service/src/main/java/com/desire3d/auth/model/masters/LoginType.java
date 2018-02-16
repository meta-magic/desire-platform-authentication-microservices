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
import com.fasterxml.jackson.annotation.JsonIgnore;

@PersistenceCapable(table = "logintype", detachable = "true")
@Version(column = "VERSION", strategy = VersionStrategy.VERSION_NUMBER, extensions = {
		@Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class LoginType implements Serializable, CommonValidator {

	private static final long serialVersionUID = 1L;

	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	@PrimaryKey
	private Integer loginTypeId;

	@Persistent
	@NotNull(message = "Logintype label should not be null")
	@Size(min = 1, max = 128, message = "LoginType label must be between 1 and 128 characters")
	private String label;

	@Persistent
	@NotNull(message = "Logintype description should not be null")
	@Size(min = 1, max = 128, message = "LoginType description must be between 1 and 128 characters")
	private String description;

	@Persistent
	@NotNull(message = "Logintype icon should not be null")
	@Size(min = 1, max = 128, message = "LoginType icon must be between 1 and 128 characters")
	private String icon;

	@Persistent
	@NotNull(message = "Logintype help should not be null")
	@Size(min = 1, max = 128, message = "LoginType help must be between 1 and 128 characters")
	private String help;

	@Persistent
	private Boolean isActive = true;

	@Persistent
	private Long version;

	@Persistent(defaultFetchGroup = "true")
	@JsonIgnore
	private MasterAuditDetails auditDetails;

	public LoginType() {

	}

	public LoginType(String label, String description, String icon, String help) {
		super();
		this.label = label;
		this.description = description;
		this.icon = icon;
		this.help = help;
	}

	public Integer getLoginTypeId() {
		return loginTypeId;
	}

	public void setLoginTypeId(Integer loginTypeId) {
		this.loginTypeId = loginTypeId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public MasterAuditDetails getAuditDetails() {
		return auditDetails;
	}

	public void setAuditDetails(MasterAuditDetails auditDetails) {
		this.auditDetails = auditDetails;
	}

}