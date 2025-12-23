package com.pairding.users.infrastructure.security.oauth2.email;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2EmailFetcherResolver {
    private final List<OAuth2EmailFetcher> fetchers;

    public OAuth2EmailFetcher resolve(String registrationId){
        return fetchers.stream()
                    .filter(f->f.supports(registrationId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported provider : " + registrationId));
    }
}
