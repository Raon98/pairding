package com.pairding.scm.infrastructure.cllient.github.dto;

import java.util.List;
import java.util.Map;

public record GithubCreateWebhookRequest(
        String url,
        String secret
) {
    public String name() {
        return "web";
    }

    public List<String> events() {
        return List.of("push");
    }

    public Map<String, Object> config() {
        return Map.of(
                "url", url,
                "content_type", "json",
                "secret", secret
        );
    }

    public boolean active() {
        return true;
    }
}