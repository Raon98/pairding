package com.pairding.users.infrastructure.security.oauth2.email;

public interface OAuth2EmailFetcher {
    boolean supports(String registrationId);
    String fetchVerifiedPrimaryEmail(String accessToken);
}
