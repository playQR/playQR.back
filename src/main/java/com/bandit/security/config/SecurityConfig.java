package com.bandit.security.config;

import com.bandit.security.exception.JwtAccessDeniedHandler;
import com.bandit.security.exception.JwtAuthenticationEntryPoint;
import com.bandit.security.filter.JwtAuthenticationFilter;
import com.bandit.security.filter.JwtExceptionFilter;
import com.bandit.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.bandit.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.bandit.security.oauth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Profile({"local","dev"})
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configureCorsAndSecurity(httpSecurity);
        configureAuth(httpSecurity);
        configureOAuth2(httpSecurity);
        configureExceptionHandling(httpSecurity);
        addFilter(httpSecurity);

        return httpSecurity.build();
    }

    private static void configureCorsAndSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers(
                        httpSecurityHeadersConfigurer ->
                                httpSecurityHeadersConfigurer.frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::disable
                                )
                )
                // stateless한 rest api 이므로 csrf 공격 옵션 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                );
    }

    private void configureAuth(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest
                            .requestMatchers("/ws/**", "/subscribe/**", "/publish/**").permitAll()
                            .requestMatchers("/", "/.well-known/**", "/css/**", "/*.ico", "/error", "/images/**").permitAll()
                            .requestMatchers(permitAllRequest()).permitAll()
                            .requestMatchers(additionalSwaggerRequests()).permitAll()
                            .requestMatchers(authRelatedEndpoints()).permitAll()
                            .anyRequest().authenticated();
//                            .requestMatchers(authorizationAdmin()).hasRole("ADMIN")
//                            .requestMatchers(authorizationDormant()).hasRole("DORMANT")
//                            .requestMatchers(authorizationGuest()).hasRole("GUEST")
//                            .requestMatchers(authorizationUser()).hasRole("USER");
                });
    }

    private void addFilter(HttpSecurity httpSecurity) {
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
    }

    private void configureExceptionHandling(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler));        // 403
    }

    private void configureOAuth2(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .authorizationEndpoint(authorizationEndpointConfig ->
                                authorizationEndpointConfig.baseUri("/oauth2/authorize"))
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler::onAuthenticationSuccess)
                        .failureHandler(oAuth2AuthenticationFailureHandler::onAuthenticationFailure)
                );
    }

    private RequestMatcher[] permitAllRequest() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(HttpMethod.GET, "/"),
                antMatcher(HttpMethod.POST, "/api/members"),
                antMatcher(HttpMethod.GET,"/api/members/{memberId}"),
                antMatcher(HttpMethod.GET, "/api/promotions/**"),
                antMatcher(HttpMethod.GET,"/api/likes/music/{promotionMusicId}/count"),
                antMatcher(HttpMethod.POST, "/api/tokens/reissue"),
                antMatcher(HttpMethod.POST, "/api/images"),
                antMatcher(HttpMethod.GET,"/api/kakao/list")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
    private RequestMatcher[] additionalSwaggerRequests() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/swagger-ui/**"),
                antMatcher("/swagger-ui"),
                antMatcher("/swagger-ui.html"),
                antMatcher("/swagger/**"),
                antMatcher("/swagger-resources/**"),
                antMatcher("/v3/api-docs/**"),
                antMatcher("/profile")

        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
    private RequestMatcher[] authRelatedEndpoints() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/oauth2/**"),
                antMatcher("/login/**"),
                antMatcher("/auth/**")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
}
