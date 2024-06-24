package com.rockoon.security.oauth.handler;

import com.rockoon.security.jwt.dto.JwtToken;
import com.rockoon.security.jwt.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${app.oauth2.authorizedRedirectUri}")
    private String redirectUri;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        JwtToken jwtToken = tokenService.generateToken(authentication);
        if (response.isCommitted()) {
            return;
        }
        String url = UriComponentsBuilder.fromHttpUrl(redirectUri)
                .queryParam("code", jwtToken.getAccessToken())
                .queryParam("refresh", jwtToken.getRefreshToken())
                .build()
                .toUriString();

        response.sendRedirect(url);
    }
}
