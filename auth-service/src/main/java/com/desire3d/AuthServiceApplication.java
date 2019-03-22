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
		String zkNodes = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_ZKNODES");
		String defaultZkPort = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_DEFAULTZKPORT");
		String headers = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_HEADERS");
		String brokers = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_BROKERS");
		String defaultBrokerPort = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_DEFAULTBROKERPORT");
		Properties props = new Properties();
		props.put("spring.cloud.stream.kafka.binder.headers", headers);
		props.put("spring.cloud.stream.kafka.binder.brokers", brokers);
		props.put("spring.cloud.stream.kafka.binder.defaultBrokerPort", defaultBrokerPort);
		props.put("spring.cloud.stream.kafka.binder.zkNodes", zkNodes);
		props.put("spring.cloud.stream.kafka.binder.defaultZkPort", defaultZkPort);
		return props;
	}

}
