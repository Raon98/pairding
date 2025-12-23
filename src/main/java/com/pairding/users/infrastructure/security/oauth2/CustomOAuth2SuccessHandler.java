package com.pairding.users.infrastructure.security.oauth2;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler; // 
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.pairding.scm.infrastructure.cllient.github.GithubApiClient;
import com.pairding.scm.infrastructure.cllient.github.dto.GithubEmailResponse;
import com.pairding.users.domain.model.UserConnection;
import com.pairding.users.domain.model.Users;
import com.pairding.users.infrastructure.db.repository.UserConnectionRepository;
import com.pairding.users.infrastructure.db.repository.UsersRepository;
import com.pairding.users.infrastructure.security.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService clientService;
    private final UsersRepository usersRepository;
    private final UserConnectionRepository connectionRepository;
    private final JwtTokenProvider jwtTokenProvider; 
    private final GithubApiClient githubApiClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        // 1. GitHub Access Token 가져오기 (외부 API 호출용)
        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());
        String githubAccessToken = client.getAccessToken().getTokenValue();

        // 2. 사용자 정보 추출
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String provider = oauthToken.getAuthorizedClientRegistrationId();
        Long providerUserId = Long.valueOf(String.valueOf(oAuth2User.getAttributes().get("id")));
        String username = (String) oAuth2User.getAttributes().get("login");
        String avatarUrl = (String) oAuth2User.getAttributes().get("avatar_url");
        String email = (String) getGithubEmail(githubAccessToken);
      
        // 3. 우리 서비스 회원 가입 또는 조회 (Upsert)
        Users user = usersRepository.findByEmail(email)
                .orElseGet(() -> usersRepository.save(
                        Users.builder()
                                .email(email)
                                .name(username)
                                .build()
                ));

        // 4. GitHub 연동 정보 저장 (UserConnection)
        boolean alreadyConnected = connectionRepository.existsByUserIdAndProvider(user.getId(), provider);
        if (!alreadyConnected) {
            connectionRepository.save(
                    UserConnection.builder()
                            .userId(user.getId())
                            .provider(provider)
                            .providerUserId(providerUserId)
                            .username(username)
                            .avatarUrl(avatarUrl)
                            .encryptedAccessToken(githubAccessToken) 
                            .build()
            );
        } else {
            connectionRepository.findByUserIdAndProvider(user.getId(), provider)
                    .ifPresent(con -> {
                        con.updateAccessToken(githubAccessToken);
                        connectionRepository.save(con);
                    });
        }

  
        // 5. Access Token, Refresh Token 생성
        String jwtAccessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), "ROLE_USER");
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()));

        // 6. DB에 Refresh Token 업데이트 (재발급 검증용)
        user.updateRefreshToken(jwtRefreshToken);
        usersRepository.save(user);


        // 예: http://localhost:3000/auth/callback?accessToken=...&refreshToken=...
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/dashboard") 
                .queryParam("accessToken", jwtAccessToken)
                .queryParam("refreshToken", jwtRefreshToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public String getGithubEmail(String token) {
        GithubEmailResponse[] emails = githubApiClient.get("/user/emails", token, GithubEmailResponse[].class);

        return Arrays.stream(emails)
                .filter(GithubEmailResponse::isPrimary)
                .filter(GithubEmailResponse::isVerified)
                .map(GithubEmailResponse::getEmail)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("사용 가능한 인증된 이메일이 없습니다."));
    }

}