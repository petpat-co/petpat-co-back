package com.smile.petpat.jwt;


import lombok.Getter;

@Getter
public class RefreshToken {

    private String refreshToken;
    private String userEmail;

    public RefreshToken(final String refreshToken, final String userEmail) {
        this.refreshToken = refreshToken;
        this.userEmail = userEmail;
    }


}

