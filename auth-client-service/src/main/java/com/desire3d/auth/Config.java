package com.desire3d.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableDiscoveryClient
@EnableZuulProxy
public class Config {

	@Value("${authservice.asyncRestTemplateProperties.poolSize}")
	private Integer poolSize;

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@LoadBalanced
	@Bean
	public AsyncRestTemplate asyncRestTemplate(@LoadBalanced RestTemplate restTemplate) {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setTaskExecutor(threadPoolTaskScheduler());
		return new AsyncRestTemplate(requestFactory, restTemplate);
	}

	@Bean(destroyMethod = "shutdown")
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		if (poolSize > 0) {
			System.out.println("*****ThreadPoolTaskScheduler Pool size set to " + poolSize);
			threadPoolTaskScheduler.setPoolSize(poolSize);
		}
		threadPoolTaskScheduler.initialize();
		return threadPoolTaskScheduler;
	}
}
