package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NameService {
	private static final String URL = "http://localhost:7070";
	private RestTemplate rest;
	private EurekaService eurekaService;
	
	public NameService(RestTemplate rest, EurekaService eurekaService) {
		this.rest = rest;
		this.eurekaService = eurekaService;
	}

	public String getName() {
		String URL = eurekaService.getServiceUrl("name");
		return rest.getForObject(URL, String.class);
	}
}
