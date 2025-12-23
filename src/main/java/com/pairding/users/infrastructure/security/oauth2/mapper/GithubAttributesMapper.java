package com.pairding.users.infrastructure.security.oauth2.mapper;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GithubAttributesMapper implements OAuth2AttributesMapper{

    @Override
    public boolean supports(String registrationId) {
        return "github".equalsIgnoreCase(registrationId);
    }

    @Override
    public OAuth2Profile map(String registrationId, Map<String, Object> attr) {

        Object idObj = attr.get("id");
        if(idObj == null) throw new IllegalArgumentException("Github attributes missing 'id'");

        String providerUserId = String.valueOf(idObj);
        String username = (String) attr.get("login");
        String avatarUrl = (String) attr.get("avatar_url");

        return new OAuth2Profile(registrationId,providerUserId,username,avatarUrl);
    }
    
}
