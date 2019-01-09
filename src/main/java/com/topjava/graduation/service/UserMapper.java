package com.topjava.graduation.service;

import com.topjava.graduation.data.Role;
import com.topjava.graduation.data.User;
import com.topjava.graduation.service.request.RequestUserTo;
import com.topjava.graduation.service.response.UserTo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    protected abstract UserTo toUserTo (User user);

    protected abstract List<UserTo> toUserTo (List<User> user);

    protected abstract User toUser(UserTo userTo);

    protected abstract User toUser(RequestUserTo requestUserTo);
    @AfterMapping
    protected void setRoles(RequestUserTo requestUserTo, @MappingTarget User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        if (Role.ROLE_ADMIN.equals(requestUserTo.getUserRole())){
            roles.add(Role.ROLE_ADMIN);
        }
        user.setRoles(roles);
    }

    protected abstract void toUser(@MappingTarget User user, RequestUserTo requestUserTo);
    @AfterMapping
    protected void setRoles(@MappingTarget User user, RequestUserTo requestUserTo) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        if (Role.ROLE_ADMIN.equals(requestUserTo.getUserRole())){
            roles.add(Role.ROLE_ADMIN);
        }
        user.setRoles(roles);
    }

}
