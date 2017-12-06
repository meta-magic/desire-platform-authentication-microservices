package com.desire3d.auth.fw.service;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

	private ClassPathResource classPathResource = new ClassPathResource("codeMessage.properties");

	private Properties properties = new Properties();

	/* READ PROPERTIES FILE AND RETURN CORRESPONDING MESSAGE FOR MESSAGECODE */
	public String getMessage(String messageCode) throws IOException {
		properties.load(classPathResource.getInputStream());
		String message = properties.getProperty(messageCode);
		return message;
	}
}
