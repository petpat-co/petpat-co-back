package com.smile.petpat.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.sql.SQLOutput;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenRepositoryTest {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void saveStringHashMap() {
        // given
        String key = "testKey";
        String email = "kkk@email.com";
        RefreshToken initRefreshToken = new RefreshToken(key,email);


        // when
        refreshTokenRepository.save(initRefreshToken);

        // then
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(key).orElseThrow(
                () -> new NullPointerException("해당 값이 없습니다.")
        );
        assertThat(refreshToken.getUserEmail()).isEqualTo("kkk@email.com");
    }



}