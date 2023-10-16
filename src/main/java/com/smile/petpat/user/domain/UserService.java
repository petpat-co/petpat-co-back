package com.smile.petpat.user.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;

public interface UserService {
    User registerUser(UserCommand command);
    HttpHeaders loginUser(UserCommand command);

    String kakaoUserLogin(String code) throws JsonProcessingException;
    String googleUserLogin(String code) throws JsonProcessingException;

    String githubUserLogin(String code) throws JsonProcessingException;
    void userIdValidChk(String userEmail);

}
