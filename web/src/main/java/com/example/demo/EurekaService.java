package com.example.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class EurekaService {
	@Autowired
	private EurekaClient eurekaClient;
    private final Map<String, String> serviceUrlCache = new ConcurrentHashMap<>();

	public String getServiceUrl(String serviceId) {
        // Check the cache first
        if (serviceUrlCache.containsKey(serviceId)) {
            return serviceUrlCache.get(serviceId);
        }

        // If not found in cache, fetch from Eureka
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(serviceId, false);
        if (instanceInfo != null) {
            String serviceUrl = instanceInfo.getHomePageUrl();
            // Cache the service URL
            serviceUrlCache.put(serviceId, serviceUrl);
            return serviceUrl;
        } else {
            throw new IllegalArgumentException("Service URL can not be found");
        }
    }
}
