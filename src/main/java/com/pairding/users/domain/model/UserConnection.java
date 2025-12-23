package com.pairding.users.domain.model;

import com.pairding.global.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "user_connections",
        indexes = {
                @Index(name = "idx_user_provider", columnList = "userId, provider"),
                @Index(name = "idx_provider_userId", columnList = "providerUserId"),
                @Index(name = "idx_provider", columnList = "provider")
        }
)
@Schema(description = "사용자 OAuth Provider 연결 정보 테이블")
public class UserConnection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 30)
    private String provider;

    @Column(unique = true, nullable = false)
    private String providerUserId;

    @Column(length = 100)
    private String username;

    @Column(length = 255)
    private String avatarUrl;

    @Column(length = 500)
    private String encryptedAccessToken;

    @Column(length = 500)
    private String encryptedRefreshToken;

    @Builder
    public UserConnection(Long id,
                          Long userId,
                          String provider,
                          String providerUserId,
                          String username,
                          String avatarUrl,
                          String encryptedAccessToken,
                          String encryptedRefreshToken) {
        this.id = id;
        this.userId = userId;
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.encryptedAccessToken = encryptedAccessToken;
        this.encryptedRefreshToken = encryptedRefreshToken;
    }

    public boolean isGithub() { return "github".equalsIgnoreCase(provider); }
    public boolean isGitlab() { return "gitlab".equalsIgnoreCase(provider); }

    public void updateAccessToken(String token) {
        this.encryptedAccessToken = token;
    }

    public void updateRefreshToken(String token) {
        this.encryptedRefreshToken = token;
    }
}
