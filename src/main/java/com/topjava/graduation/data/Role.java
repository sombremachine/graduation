package com.topjava.graduation.data;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_ROOT;

    @Override
    public String getAuthority() {
        return name();
    }
}
