/**
 * Component used for client side load balancing using ribbon
 * 
 */
package com.desire3d.auth.component;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

/**
 * @author Mahesh Pardeshi
 *
 */
@Component
public class LoadBalancer {

	@Autowired
	private LoadBalancerClient loadBalancer;

	/** Server's service id registered with eureka e.g. authservice */
	@Value("${authservice.ribbon.DeploymentContextBasedVipAddresses}")
	private String serviceId;

	/**
	 * Method used to get the load balanced url of service among the options available 
	 * 
	 * @param serviceId - Service ID of the server service for load balancing e.g. authservice
	 * 					   
	 * */
	public final String getServiceURL(String serviceId) {
		return generateURL(serviceId);
	}

	/**
	 * Method used to get the load balanced url of service among the options available 
	 * */
	public final String getServiceURL() {
		return generateURL(this.serviceId);
	}

	private final String generateURL(String serviceId) {
		ServiceInstance instance = loadBalancer.choose(serviceId);
		String authserviceUrl = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort())).toString();
		System.out.println("*****Load Balancer for service " + serviceId + ", Chosen server url : ***** " + authserviceUrl);
		return authserviceUrl;
	}
}
