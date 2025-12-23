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
import com.pairding.users.infrastructure.security.oauth2.email.OAuth2EmailFetcherResolver;
import com.pairding.users.infrastructure.security.oauth2.mapper.OAuth2AttributesMapperResolver;
import com.pairding.users.infrastructure.security.oauth2.mapper.OAuth2Profile;

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

    private final OAuth2AttributesMapperResolver attributesMapperResolver;
    private final OAuth2EmailFetcherResolver emailFetcherResolver;
        
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(provider,oauthToken.getName());
        String githubAccessToken = client.getAccessToken().getTokenValue();

        OAuth2User oAuth2User = oauthToken.getPrincipal();
        OAuth2Profile profile = attributesMapperResolver
                                        .resolve(githubAccessToken)
                                        .map(githubAccessToken, oAuth2User.getAttributes());

        String email = emailFetcherResolver
                        .resolve(githubAccessToken)
                        .fetchVerifiedPrimaryEmail(githubAccessToken);
      
       
        Users user = usersRepository.findByEmail(email)
                .orElseGet(() -> usersRepository.save(
                        Users.builder()
                                .email(email)
                                .name(profile.username())
                                .build()
                ));

        boolean alreadyConnected = connectionRepository.existsByUserIdAndProvider(user.getId(), provider);
        if (!alreadyConnected) {
            connectionRepository.save(
                    UserConnection.builder()
                            .userId(user.getId())
                            .provider(provider)
                            .providerUserId(profile.providerUserId())
                            .username(profile.username())
                            .avatarUrl(profile.avatarUrl())
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


        String jwtAccessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), "ROLE_USER");
        String jwtRefreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()));

        user.updateRefreshToken(jwtRefreshToken);
        usersRepository.save(user);

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/dashboard") 
                .queryParam("accessToken", jwtAccessToken)
                .queryParam("refreshToken", jwtRefreshToken)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}