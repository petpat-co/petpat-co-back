package com.smile.petpat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.security.SecureRandom;

@Configuration
public class AppConfig {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

    @Bean
    public HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }
}
