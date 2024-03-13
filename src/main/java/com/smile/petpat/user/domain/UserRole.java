package com.smile.petpat.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ROLE_USER("ROLE_USER", "일반 사용자 권한"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자 권한"),
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", "관리자 관리 권한"),
    ROLE_GUEST("ROLE_GUEST", "게스트 권한");

    private final String code;
    private final String displayName;

}

