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
		String user = System.getenv("AUTH_MS_DB_USER");
		String password = System.getenv("AUTH_MS_DB_PASSWORD");
		String connectionurl = System.getenv("AUTH_DB_CONNECTION_URL");
		Properties prop = new Properties();
		prop.setProperty("javax.jdo.option.ConnectionURL", connectionurl);
		prop.setProperty("javax.jdo.option.ConnectionDriverName", "org.postgresql.Driver");
		prop.setProperty("javax.jdo.option.ConnectionUserName", user);
		prop.setProperty("javax.jdo.option.DetachAllOnCommit", "true");
		prop.setProperty("javax.jdo.option.Mapping", "postgres");
		prop.setProperty("javax.jdo.option.ConnectionPassword", password);
		prop.setProperty("datanucleus.schema.autoCreateAll", "true");
		prop.setProperty("datanucleus.schema.validateConstraints", "false");
		prop.setProperty("datanucleus.schema.validateTables", "false");
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(prop);
		return pmf;
	}
}
