package com.pairding.users.infrastructure.security.oauth2.mapper;

import java.util.Map;

public interface OAuth2AttributesMapper {
    boolean supports(String registrationId);
    OAuth2Profile map(String registrationId, Map<String, Object> attr);
}
