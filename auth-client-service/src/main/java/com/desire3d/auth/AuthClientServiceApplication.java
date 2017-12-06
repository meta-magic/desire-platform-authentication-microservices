package com.desire3d.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class AuthClientServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AuthClientServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthClientServiceApplication.class);
	}
}
