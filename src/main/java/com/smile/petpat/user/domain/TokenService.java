package com.smile.petpat.user.domain;

import org.springframework.http.HttpHeaders;

public interface TokenService {

    HttpHeaders regeneratedAccessToken(String refreshToken, String accessToken);
    HttpHeaders regeneratedRefreshToken( String invalidedRefreshToken, String invalidedAccessToken);
}
