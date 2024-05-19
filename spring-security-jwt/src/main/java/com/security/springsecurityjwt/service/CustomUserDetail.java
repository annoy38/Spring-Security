package com.security.springsecurityjwt.service;

import com.security.springsecurityjwt.model.Role;
import com.security.springsecurityjwt.model.UserClass;
import com.security.springsecurityjwt.repository.IUserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetail implements UserDetails {
    private UserClass userClass;
    public CustomUserDetail(Optional<UserClass> user) {
        this.userClass = user.orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
            return userClass.roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return userClass.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userClass.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
