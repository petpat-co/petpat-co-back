package com.smile.petpat.security;

import com.smile.petpat.annotation.WithMockCustomUser;
import com.smile.petpat.user.domain.User;
import com.smile.petpat.user.domain.UserRole;
import com.smile.petpat.user.service.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        String userEmail = annotation.userEmail();
        String nickname = annotation.nickname();
        UserRole userRole = annotation.userRole();

        User user = User.builder()
                .userEmail(userEmail)
                .nickname(nickname)
                .userRole(userRole)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, "password", Collections.singletonList(new SimpleGrantedAuthority(userRole.name())));
        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
