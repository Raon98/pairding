package com.pairding.users.infrastructure.security.oauth2.email;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.pairding.scm.infrastructure.cllient.github.GithubApiClient;
import com.pairding.scm.infrastructure.cllient.github.dto.GithubEmailResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GithubEmailFetcher implements OAuth2EmailFetcher{

    private final GithubApiClient githubApiClient;

    @Override
    public boolean supports(String registrationId) {
        return "github".equalsIgnoreCase(registrationId);
    }

    @Override
    public String fetchVerifiedPrimaryEmail(String accessToken) {
        GithubEmailResponse[] emails = githubApiClient.get("/user/emails", accessToken, GithubEmailResponse[].class);

        return Arrays.stream(emails)
                .filter(GithubEmailResponse::isPrimary)
                .filter(GithubEmailResponse::isVerified)
                .map(GithubEmailResponse::getEmail)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("사용 가능한 인증된 이메일이 없습니다."));
    }
    
}
