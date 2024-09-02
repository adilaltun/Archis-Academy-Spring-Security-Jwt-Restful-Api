package com.works.archisproject.dto.request;

import com.works.archisproject.entity.Role;

import java.util.Set;

public record CreateUserRequest (
        String name,
        String email,
        String password,
        Set<Role> authorities
){

}
