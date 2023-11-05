package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.cloud.config.enabled:false" })
@TestPropertySource(locations = { "classpath:test.properties" })
public class GreetingApplicationTests {

	@Autowired
	TestRestTemplate rest;

	@Test
	public void contextLoads() {
		String deGreeting = this.rest.getForObject("/de", String.class);
		String enGreeting = this.rest.getForObject("/en", String.class);
		String esGreeting = this.rest.getForObject("/es", String.class);
		String defaultGreeting = this.rest.getForObject("/", String.class);
		assertEquals("Hallo", deGreeting);
		assertEquals("Hello", enGreeting);
		assertEquals("Hola", esGreeting);
		assertEquals("Hello", defaultGreeting);
	}
}
