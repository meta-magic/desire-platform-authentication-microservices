package com.desire3d;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDOConfiguration {

	@Bean
	public PersistenceManagerFactory pmf() {
		PersistenceManagerFactory PERSISTENCE_MANAGER_FACTORY = JDOHelper.getPersistenceManagerFactory("PersistenceUnit");
		return PERSISTENCE_MANAGER_FACTORY;
	}

}
