
package com.desire3d;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.client.RestTemplate;

import com.desire3d.auth.dto.VersionInfo;
import com.desire3d.channel.NotificationChannel;
import com.desire3d.channel.PasswordRecoveryChannel;
import com.desire3d.channel.SessionHandlerChannel;
import com.desire3d.channel.UserCreationChannel;

@RefreshScope
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
@EnableBinding({ UserCreationChannel.class, NotificationChannel.class, PasswordRecoveryChannel.class,
		SessionHandlerChannel.class })
public class Config {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public VersionInfo getVersion() {
		String version = System.getenv("SERVICE_VERSION");
		System.out.println("*****version******" + version);
		if (version == null) {
			version = "v1";
		}
		return new VersionInfo(version);
	}

}