package com.pairding.users.infrastructure.security.oauth2.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AttributesMapperResolver {
    private final List<OAuth2AttributesMapper> mapper;

    public OAuth2AttributesMapper resolve(String registrationId){
        return mapper.stream()
                    .filter(m -> m.supports(registrationId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unsupported Provider :" + registrationId));
    }

}
