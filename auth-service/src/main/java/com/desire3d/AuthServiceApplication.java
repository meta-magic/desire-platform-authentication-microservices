package com.desire3d;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class AuthServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication sa = new SpringApplication(AuthServiceApplication.class);
		sa.setDefaultProperties(getProperties());
		sa.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		application.properties(getProperties());
		return application.sources(AuthServiceApplication.class);
	}

	public static Properties getProperties() {
		KafkaConfiguration kafkaConfiguration = new KafkaConfiguration();
		Properties props = new Properties();
		System.out.println("\n zkNodes: " + kafkaConfiguration.getZkNodes() + "\n defaultZkPort:"
				+ kafkaConfiguration.getDefaultZkPort() + "\n brokers:" + kafkaConfiguration.getBrokers()
				+ "\n defaultBrokerPort:" + kafkaConfiguration.getDefaultBrokerPort());
		props.put("spring.cloud.stream.kafka.binder.headers", "tokenId");
		props.put("spring.cloud.stream.kafka.binder.brokers", kafkaConfiguration.getBrokers());
		props.put("spring.cloud.stream.kafka.binder.defaultBrokerPort", kafkaConfiguration.getDefaultBrokerPort());
		props.put("spring.cloud.stream.kafka.binder.zkNodes", kafkaConfiguration.getZkNodes());
		props.put("spring.cloud.stream.kafka.binder.defaultZkPort", kafkaConfiguration.getDefaultZkPort());
		return props;
	}

}
