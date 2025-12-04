package com.pairding.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient githubWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader("Accept", "application/vnd.github+json")
                .build();
    }

    @Bean
    public WebClient gitlabWebClient() {
        return WebClient.builder()
                .baseUrl("https://gitlab.com/api/v4")
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
