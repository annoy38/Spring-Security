package com.security.springsecurityjwt.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDTOWithRole {
    private Long id;
    private String username;
    private String userEmail;
    private Set<Role> roles;

    public UserDTOWithRole() {

    }

    public UserDTOWithRole(Long id, String username, String userEmail, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.userEmail = userEmail;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
