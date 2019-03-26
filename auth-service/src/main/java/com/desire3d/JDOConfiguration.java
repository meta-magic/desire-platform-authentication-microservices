package com.desire3d;

import java.util.Properties;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDOConfiguration {

	@Bean
	public PersistenceManagerFactory getPersistenceManagerFactory() {
		Properties prop = new Properties();
		prop.setProperty("javax.jdo.option.ConnectionURL", SystemEnviroment.getConnectionurl());
		prop.setProperty("javax.jdo.option.ConnectionDriverName", "org.postgresql.Driver");
		prop.setProperty("javax.jdo.option.ConnectionUserName", SystemEnviroment.getUser());
		prop.setProperty("javax.jdo.option.DetachAllOnCommit", "true");
		prop.setProperty("javax.jdo.option.Mapping", "postgres");
		prop.setProperty("javax.jdo.option.ConnectionPassword", SystemEnviroment.getPassword());
		prop.setProperty("datanucleus.schema.autoCreateAll", "false");
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(prop);
		return pmf;
	}
}
