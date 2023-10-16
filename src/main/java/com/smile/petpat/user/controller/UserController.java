package com.smile.petpat.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smile.petpat.jwt.TokenProvider;
import com.smile.petpat.user.domain.UserCommand;
import com.smile.petpat.user.dto.UserDto;
import com.smile.petpat.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;
    private final TokenProvider tokenProvider;

    // 회원가입
    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public void userRegister(@Validated @RequestBody UserDto.RegisterUserRequest request){
        UserCommand command = request.toCommand();
        userService.registerUser(command);
    }

    // 이메일 중복검사
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void usernameValidChk(@PathVariable String id){
        userService.userIdValidChk(id);
    }

    // 로그인
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> userLogin(@Validated @RequestBody UserDto.LoginUserRequest loginUserRequest, HttpServletRequest request){
        UserCommand command = loginUserRequest.toCommand();
        HttpHeaders httpHeaders = userService.loginUser(command);

        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(null);
    }

    // 카카오 로그인
    @RequestMapping(value = "/kakao/callback", method = RequestMethod.GET)
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = userService.kakaoUserLogin(code);

        response.addHeader("Authorization", "Bearer " + token);
    }

    // 구글 로그인
    @RequestMapping(value = "/google/callback", method = RequestMethod.GET)
    public void googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = userService.googleUserLogin(code);

        response.addHeader("Authorization", "Bearer " + token);
    }

    // 깃허브 로그인
    @RequestMapping(value = "/github/callback", method = RequestMethod.GET)
    public void githubLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = userService.githubUserLogin(code);

        response.addHeader("Authorization", "Bearer " + token);
    }
}
