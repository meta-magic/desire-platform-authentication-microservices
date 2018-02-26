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

	private static final long serialVersionUID = -728883814115792216L;

	private final String toAddress;

	private final String subject;

	private final Set<String> cc;

	private final Set<String> bcc;

	private final String templateId;

	private final Map<String, Object> dynamicContent;

	public EmailNotificationEvent() {
		super();
		this.toAddress = null;
		this.subject = null;
		this.cc = null;
		this.bcc = null;
		this.templateId = null;
		this.dynamicContent = null;
	}

	/**
	 * Construct an object without subject 
	 * 
	 * @param toAddress
	 * @param templateId
	 * @param dynamicContent
	 */
	public EmailNotificationEvent(String toAddress, String templateId, Map<String, Object> dynamicContent) {
		super();
		this.toAddress = toAddress;
		this.templateId = templateId;
		this.dynamicContent = dynamicContent;
		this.subject = null;
		this.cc = null;
		this.bcc = null;
	}
	
	/**
	 * Construct an object with bcc & without subject 
	 * 
	 * @param toAddress
	 * @param bcc
	 * @param templateId
	 * @param dynamicContent
	 */
	public EmailNotificationEvent(String toAddress, Set<String> bcc, String templateId, Map<String, Object> dynamicContent) {
		super();
		this.toAddress = toAddress;
		this.bcc = bcc;
		this.templateId = templateId;
		this.dynamicContent = dynamicContent;
		this.subject = null;
		this.cc = null;
	}

	/**
	 * Construct an object with subject 
	 * 
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

	/**
	 * Construct an object with subject, cc
	 * 
	 * @param toAddress
	 * @param subject
	 * @param cc
	 * @param templateId
	 * @param dynamicContent
	 */
	public EmailNotificationEvent(String toAddress, String subject, Set<String> cc, String templateId, Map<String, Object> dynamicContent) {
		super();
		this.toAddress = toAddress;
		this.subject = subject;
		this.cc = cc;
		this.bcc = null;
		this.templateId = templateId;
		this.dynamicContent = dynamicContent;
	}

	/**
	 * Construct an object with subject, cc && bcc
	 * 
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