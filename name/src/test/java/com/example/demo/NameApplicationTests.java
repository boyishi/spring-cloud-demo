package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"spring.cloud.config.enabled:false"})
@TestPropertySource(locations = "classpath:test.properties")
public class NameApplicationTests {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void contextLoads() {
        String name = rest.getForObject("/", String.class);
        assertEquals("Ryan", name);
    }

}
