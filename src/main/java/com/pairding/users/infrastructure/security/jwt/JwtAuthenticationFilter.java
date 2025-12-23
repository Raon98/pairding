package com.pairding.users.infrastructure.security.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${app.swagger.token:}")
    private String swaggerToken;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        request = wrapSwaggerAuthorizationIfNeeded(request);
        String token = resolveToken(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
    private HttpServletRequest wrapSwaggerAuthorizationIfNeeded(HttpServletRequest request) {
        if (!StringUtils.hasText(swaggerToken)) return request;
        String referer = request.getHeader("Referer");
        boolean fromSwagger = referer != null && referer.contains("/swagger-ui");
        if (!fromSwagger) return request;
        boolean isApiCall = request.getRequestURI().startsWith("/api/");
        if (!isApiCall) return request;
        String existingAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(existingAuth)) return request;
        String bearer = swaggerToken.startsWith("Bearer ")
                ? swaggerToken
                : "Bearer " + swaggerToken;
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                if ("Authorization".equalsIgnoreCase(name)) {
                    return bearer;
                }
                return super.getHeader(name);
            }
        };
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}