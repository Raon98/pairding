package com.pairding.global.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

     private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws java.io.IOException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Object userId = oauthUser.getAttribute("id");

        // 클라이언트 URL
        String clientUrl = request.getHeader("X-Client-Url");
        if (clientUrl == null || clientUrl.isEmpty()) {
            clientUrl = "http://localhost:3000";
        }

        if (userId == null) {
            response.sendRedirect(clientUrl + "/signup");
        } else {
            String token = jwtProvider.generateToken(authentication);
            response.addHeader("Authorization", "Bearer " + token);
            response.sendRedirect(clientUrl + "/dashboard");
        }
    }
}
