package com.smile.petpat.user.service;

import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.domain.UserRole;
import com.smile.petpat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl{
    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user =  userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + userEmail));;

        return new UserDetailsImpl(user);
    }

    // TODO : 비회원으로 API 호출 시 makeGuest() 함수가 두 번씩 호출됨, 추후 수정 필요
    public UserDetails makeGuest() {
        log.info("makeGuest() 함수 호출");

        Random random = new Random();
        User user = User.builder()
                .userEmail("guest"+ random.nextInt() +"@guest.com")
                .password("guest")
                .nickname("guest" + random.nextInt())
                .userRole(UserRole.ROLE_GUEST)
                .build();

        return  new UserDetailsImpl(user);
    }
}
