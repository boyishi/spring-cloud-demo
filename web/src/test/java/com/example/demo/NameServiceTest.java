package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class NameServiceTest {

    @Test
    public void getNameTest() {
        RestTemplate rest = mock(RestTemplate.class);
        EurekaService eurekaService = mock(EurekaService.class);
        doReturn("Ryan").when(rest).getForObject(eq("http://localhost:7070"), eq(String.class));
        NameService nameService = new NameService(rest, eurekaService);
        String name = nameService.getName();
        assertEquals("Ryan", name);
    }
}
