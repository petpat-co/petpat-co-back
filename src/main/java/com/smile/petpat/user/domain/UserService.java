package com.smile.petpat.user.domain;

public interface UserService {
    User registerUser(UserCommand command);
    void usernameValidChk(String username);

}
