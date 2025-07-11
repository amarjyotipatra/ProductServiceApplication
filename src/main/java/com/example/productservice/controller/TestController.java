package com.example.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
// Calling /test endpoint of Email Service with the help of RestTemplate and Eureka Server

    @Autowired
    private RestTemplate restTemplate;

    /*
    localhost:8080/test
     */
    @GetMapping("/email/test")
    public String callingEmailService() {
        System.out.println("Running TestController inside product service");
        ResponseEntity<String> response = restTemplate.getForEntity("http://emailserviceapplication/test", String.class);
        return response.getBody();
    }
}
