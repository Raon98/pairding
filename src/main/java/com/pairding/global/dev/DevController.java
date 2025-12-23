package com.pairding.global.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pairding.users.infrastructure.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Profile("local")
@RestController
@RequiredArgsConstructor
public class DevController {
    
    private final JwtTokenProvider jwtProvider;

    @GetMapping("/api/dev/token")
    public String createDevToken (@RequestParam(defaultValue = "1") String userid, @RequestParam(defaultValue = "USER") String role){
        return jwtProvider.createAccessToken(userid, role);
    }
}
