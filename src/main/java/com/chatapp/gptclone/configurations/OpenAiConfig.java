package com.chatapp.gptclone.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rest = new RestTemplate();
        // Add interceptor to include Authorization header
        ClientHttpRequestInterceptor authInterceptor = (request, body, execution) -> {
            request.getHeaders().setBearerAuth(apiKey);
            request.getHeaders().set("Content-Type", "application/json");
            return execution.execute(request, body);
        };
        rest.setInterceptors(Collections.singletonList(authInterceptor));
        return rest;
    }
}
