package com.desire3d;

public class KafkaConfiguration {
	
	String zkNodes = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_ZKNODES");
	String defaultZkPort = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_DEFAULTZKPORT");
	String brokers = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_BROKERS");
	String defaultBrokerPort = System.getenv("SPRING_CLOUD_STREAM_KAFKA__BINDER_DEFAULTBROKERPORT");

	public String getZkNodes() {
		return zkNodes;
	}

	public String getDefaultZkPort() {
		return defaultZkPort;
	}

	public String getBrokers() {
		return brokers;
	}

	public String getDefaultBrokerPort() {
		return defaultBrokerPort;
	}

}
