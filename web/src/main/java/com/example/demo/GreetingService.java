package com.example.demo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@Service
public class GreetingService {
	private RestTemplate rest;
	private EurekaService eurekaService;
	
	public GreetingService(RestTemplate rest, EurekaService eurekaService) {
		this.rest= rest;
		this.eurekaService = eurekaService;
	}

	public String getGreeting() {
		String URL = eurekaService.getServiceUrl("greeting");
		return rest.getForObject(URL, String.class);
	}

	public String getGreeting(String locale) {
		String URL = eurekaService.getServiceUrl("greeting");
		return rest.getForObject(new StringBuilder().append(URL).append("/").append(locale).toString(), String.class);
	}
}
