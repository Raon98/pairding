package com.pairding.scm.adapter.github;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GithubApiClient {
    private final WebClient gitlabWebClient;

    public <T> T get(String url, String token, Class<T> clazz) {
        return gitlabWebClient.get()
                .uri(url)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(clazz)
                .block();
    }

    public <T> T post(String url, String token, Object body, Class<T> clazz) {
        return gitlabWebClient.post()
                .uri(url)
                .headers(h -> h.setBearerAuth(token))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(clazz)
                .block();
    }
}