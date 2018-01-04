package com.desire3d.event;

import java.util.Map;
import java.util.Set;

/**
 * Event is used to transmit details to send email
 * 
 * @author Mahesh Pardeshi
 *
 */
public final class EmailNotificationEvent implements IntegrationEvent {

	private static final long serialVersionUID = -6691776231160725L;

	private final String toAddress;

	private final String subject;

	private final Set<String> cc;

	private final Set<String> bcc;

	private final String templateId;

	private final Map<String, Object> dynamicContent;

	/**
	 * @param toAddress
	 * @param subject
	 * @param cc
	 * @param bcc
	 * @param templateId
	 */
	public EmailNotificationEvent(String toAddress, String subject, Set<String> cc, Set<String> bcc, String templateId) {
		super();
		this.toAddress = toAddress;
		this.subject = subject;
		this.cc = cc;
		this.bcc = bcc;
		this.templateId = templateId;
		this.dynamicContent = null;
	}

	/**
	 * @param toAddress
	 * @param subject
	 * @param cc
	 * @param bcc
	 * @param templateId
	 * @param dynamicContent
	 */
	public EmailNotificationEvent(String toAddress, String subject, Set<String> cc, Set<String> bcc, String templateId, Map<String, Object> dynamicContent) {
		super();
		this.toAddress = toAddress;
		this.subject = subject;
		this.cc = cc;
		this.bcc = bcc;
		this.templateId = templateId;
		this.dynamicContent = dynamicContent;
	}

	/**
	 * @param toAddress
	 * @param subject
	 * @param templateId
	 * @param dynamicContent
	 */
	public EmailNotificationEvent(String toAddress, String subject, String templateId, Map<String, Object> dynamicContent) {
		super();
		this.toAddress = toAddress;
		this.subject = subject;
		this.templateId = templateId;
		this.dynamicContent = dynamicContent;
		this.cc = null;
		this.bcc = null;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public Set<String> getCc() {
		return cc;
	}

	public Set<String> getBcc() {
		return bcc;
	}

	public String getTemplateId() {
		return templateId;
	}

	public Map<String, Object> getDynamicContent() {
		return dynamicContent;
	}
}