package com.desire3d;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class AuthServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		System.out.println("\n ConnectionURL :" + SystemEnviroment.getConnectionurl() + "\n User :"
				+ SystemEnviroment.getUser() + "\n Tokenkey :" + SystemEnviroment.getTokenKey() + "\n TokenValidity :"
				+ SystemEnviroment.getTokenValidity() + "\n Sessionexpiry :" + SystemEnviroment.getSessionexpiry());

		SpringApplication sa = new SpringApplication(AuthServiceApplication.class);
		sa.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("\n ConnectionURL :" + SystemEnviroment.getConnectionurl() + "\n User :"
				+ SystemEnviroment.getUser() + "\n Tokenkey :" + SystemEnviroment.getTokenKey() + "\n TokenValidity :"
				+ SystemEnviroment.getTokenValidity() + "\n Sessionexpiry :" + SystemEnviroment.getSessionexpiry());

		return application.sources(AuthServiceApplication.class);
	}

}
