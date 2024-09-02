package com.works.archisproject.entity;

import org.springframework.security.core.GrantedAuthority;

// GrantedAuthority rolleri standart hale getirmek için kullanılan bir interface.
public enum Role implements GrantedAuthority {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_MOD("MOD");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    // GrantedAuthority içerisindeki method.
    @Override
    public String getAuthority() {
        return name();
    }
}
