package com.pairding.users.infrastructure.security.oauth2.mapper;

public record OAuth2Profile(
    String provider,
    String providerUserId,
    String username,
    String avatarUrl
){}
