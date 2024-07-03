package com.smile.petpat.annotation;

import com.smile.petpat.security.WithMockUserSecurityContextFactory;
import com.smile.petpat.user.domain.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String userEmail() default "testUser@test.com";
    String nickname() default "testUser";
    UserRole userRole() default UserRole.ROLE_USER;

}
