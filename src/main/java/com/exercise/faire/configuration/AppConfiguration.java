package com.exercise.faire.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author eder
 */
@Configuration
@ComponentScan(basePackages = "com.exercise.faire")
public class AppConfiguration {
    
    @Value("${api.key}")
    private String apiKey;
    
    @Bean
    public RestTemplate restTemplate() {
        
        final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        restTemplate.getInterceptors().add((ClientHttpRequestInterceptor) (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
            
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            request.getHeaders().add("X-FAIRE-ACCESS-TOKEN", apiKey);
            
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
