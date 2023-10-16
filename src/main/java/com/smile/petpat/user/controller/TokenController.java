package com.smile.petpat.user.controller;

import com.smile.petpat.user.service.TokenServiceImpl;
import com.smile.petpat.user.service.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Tag(name = "TokenController", description = "토큰관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class TokenController {

    private final TokenServiceImpl tokenService;

    @Operation(summary = "accessToken 재발급", description = "accessToken 재발급")
    @RequestMapping(value = "/accessToken",method = RequestMethod.POST)
    public ResponseEntity<String> regeneratedAccessToken(HttpServletRequest request){

        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");
        log.info("accessToken : {}",accessToken);
        log.info("refreshToken : {} ",refreshToken);

        HttpHeaders httpHeaders = tokenService.regeneratedAccessToken(refreshToken, accessToken);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
    }

    @RequestMapping(value = "/refreshToken",method = RequestMethod.POST)
    public ResponseEntity<String> regeneratedRefreshToken(HttpServletRequest request){

        String accessToken = request.getHeader("Authorization");
        String refreshToken = request.getHeader("RefreshToken");
        log.info("accessToken : {}",accessToken);
        log.info("refreshToken : {} ",refreshToken);

        HttpHeaders httpHeaders = tokenService.regeneratedRefreshToken(refreshToken, accessToken);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
    }
}
