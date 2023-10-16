package com.smile.petpat.user.service;

import com.smile.petpat.jwt.JwtTokenUtils;
import com.smile.petpat.jwt.RefreshTokenManager;
import com.smile.petpat.user.domain.TokenService;
import com.smile.petpat.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenManager refreshTokenManager;
    private final HttpHeaders headers;
    public HttpHeaders regeneratedAccessToken(String refreshToken, String accessToken) {
        String userEmail = refreshTokenManager.isValidRefreshToken(refreshToken);
        jwtTokenUtils.generateJwtToken(userEmail);
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer " + accessToken);

        return headers;
    }

    public HttpHeaders regeneratedRefreshToken( String invalidedRefreshToken, String invalidedAccessToken) {
        String userEmail = refreshTokenManager.isValidRefreshToken(invalidedRefreshToken);
        String validAccessToken = jwtTokenUtils.generateJwtToken(userEmail);
        String validRefreshToken = refreshTokenManager.saveRefreshToken(userEmail);
        headers.set(HttpHeaders.AUTHORIZATION,"Bearer " + validAccessToken);
        headers.set("RefreshToken",validRefreshToken);
        return headers;

    }
}
