package com.desire3d.auth.domainservice;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.desire3d.auth.exceptions.BaseException;
import com.desire3d.auth.fw.domainservice.MessageService;

@Component
public class MessageServiceImpl implements MessageService {

	private ClassPathResource classPathResource = new ClassPathResource("codeMessage.properties");

	private Properties properties = new Properties();

	private Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

	/**
	 * GET MESSAGE FROM codeMessage.properties
	 * 
	 * @param messageId
	 * @param throwable
	 * @return message
	 * @throws IOException
	 */
	@Override
	public String getMessageById(String messageId, Throwable throwable) {
		String message;
		try {
			message = this.getMessage(messageId);
		} catch (IOException e) {
			LOGGER.info("No message attached for " + messageId + " Please define message in codeMessage.properties file");
			message = throwable.getMessage();
		}
		return message;
	}

	/**
	 * GET MESSAGE FROM codeMessage.properties
	 * 
	 * @param messageId
	 * @return message
	 * @throws IOException
	 */
	@Override
	public String getMessageById(String messageId) {
		String message;
		try {
			message = this.getMessage(messageId);
		} catch (IOException e) {
			message = "Service processing failed. " + " Exception id : " + messageId;
			LOGGER.info("No message attached for " + messageId + " Please define message in codeMessage.properties file");
		}
		return message;
	}

	/**
	 * GET MESSAGE FOR {@link BaseException} FROM codeMessage.properties
	 * 
	 * @return message
	 * @param exception {@link BaseException}
	 */
	@Override
	public String getExceptionMessage(BaseException exception) {
		return getMessageById(exception.getMessageId(), exception);
	}

	/* READ PROPERTIES FILE AND RETURN CORRESPONDING MESSAGE FOR MESSAGECODE */
	private String getMessage(String messageCode) throws IOException {
		properties.load(classPathResource.getInputStream());
		String message = properties.getProperty(messageCode);
		return message;
	}
}