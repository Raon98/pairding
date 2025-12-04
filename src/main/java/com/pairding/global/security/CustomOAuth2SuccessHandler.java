package com.pairding.global.security;

import com.pairding.users.domain.UserConnection;
import com.pairding.users.domain.Users;
import com.pairding.users.repository.UserConnectionRepository;
import com.pairding.users.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthorizedClientService clientService;
    private final UsersRepository usersRepository;
    private final UserConnectionRepository connectionRepository;
    private final TsidGenerator tsidGenerator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        oauthToken.getAuthorizedClientRegistrationId(),
                        oauthToken.getName());

        String accessToken = client.getAccessToken().getTokenValue();

        OAuth2User oAuth2User = oauthToken.getPrincipal();

        String provider = oauthToken.getAuthorizedClientRegistrationId();
        Long providerUserId = Long.valueOf(oAuth2User.getAttribute("id").toString());
        String username = oAuth2User.getAttribute("login");
        String avatarUrl = oAuth2User.getAttribute("avatar_url");
        String email = oAuth2User.getAttribute("email");

        Users user = usersRepository.findByEmail(email)
                .orElseGet(() -> usersRepository.save(
                        Users.builder()
                                .email(email)
                                .name(username)
                                .build()
                ));

        boolean alreadyConnected =
                connectionRepository.existsByUserIdAndProvider(user.getId(), provider);

        if (!alreadyConnected) {
            connectionRepository.save(
                    UserConnection.builder()
                            .userId(user.getId())
                            .provider(provider)
                            .providerUserId(providerUserId)
                            .username(username)
                            .avatarUrl(avatarUrl)
                            .encryptedAccessToken(accessToken)
                            .build()
            );
        } else {
            connectionRepository.findByUserIdAndProvider(user.getId(), provider)
                    .ifPresent(con -> {
                        con.updateAccessToken(accessToken);
                        connectionRepository.save(con);
                    });
        }

        response.sendRedirect("/dashboard");
    }
}
