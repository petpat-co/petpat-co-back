package com.smile.petpat.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smile.petpat.jwt.JwtTokenUtils;
import com.smile.petpat.jwt.RefreshTokenManager;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.domain.UserAuth;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserAuthImpl implements UserAuth {

    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenManager refreshTokenManager;
    private final HttpHeaders headers;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;
    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubClientSecret;
    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String githubRedirectUri;

    @Override
    public String getToken(User user) {
        return jwtTokenUtils.generateJwtToken(user.getUserEmail());
    }

    @Override
    public String saveRefreshTokenToRedis(User user){
      return refreshTokenManager.saveRefreshToken(user.getUserEmail());
    }

    @Override
    public HttpHeaders generateHeaderTokens(User user) {
        String accessToken = getToken(user);
        String refreshToken = saveRefreshTokenToRedis(user);
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer " + accessToken);
        headers.set("RefreshToken",refreshToken);
        return headers;
    }

    @Override
    public String getKakaoAccessToken(String code) throws JsonProcessingException {
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("redirect_uri", kakaoRedirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseToken = objectMapper.readTree(responseBody);

        return responseToken.get("access_token").asText();
    }

    @Override
    public String getGoogleAccessToken(String code) throws JsonProcessingException {
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("redirect_uri", googleRedirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseToken = objectMapper.readTree(responseBody);
        return responseToken.get("access_token").asText();
    }

    @Override
    public String getGithubAccessToken(String code) throws JsonProcessingException {
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", githubClientId);
        body.add("client_secret", githubClientSecret);
        body.add("redirect_uri", githubRedirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> githubTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://github.com/login/oauth/access_token",
                HttpMethod.POST,
                githubTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseToken = objectMapper.readTree(responseBody);
        return responseToken.get("access_token").asText();
    }

    @Override
    public String forceLogin(User socialUser) {
        UserDetails userDetails = new User(socialUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        return jwtTokenUtils.generateJwtToken(user.getUserEmail());
    }

}
