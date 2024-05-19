package com.security.springsecurityjwt.service;

import com.security.springsecurityjwt.model.*;
import com.security.springsecurityjwt.repository.IUserRepository;
import com.security.springsecurityjwt.repository.UserRepositoryDAO;
import com.security.springsecurityjwt.security.JWTUtilClass;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceClass {
    private static final Logger log = LoggerFactory.getLogger(UserServiceClass.class);
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtilClass utilClass;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepositoryDAO userRepositoryDAO;

    public void saveUser(UserClass user) {
        String encodePassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodePassword);
        userRepository.save(user);
    }

    public boolean isExitUser(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }

    public String generateToken(LoginRequest request){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getUserPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return utilClass.generateToken(request.getUserEmail());
        }catch (Exception e){
            log.error("Authentication Failed "+e.getMessage());
            return null;
        }
    }

    public List<UserDTOClass> getAllUser(){
        List<UserDTOClass> userDTOClassList = new ArrayList<>();
        for(UserClass user: userRepository.getAllUserData()){
            UserDTOClass dtoClass = new UserDTOClass();
            dtoClass.setUserId(user.getUserId());
            dtoClass.setUserName(user.getUserName());
            dtoClass.setUserEmail(user.getUserEmail());
            userDTOClassList.add(dtoClass);
        }
        return userDTOClassList;
    }

    public UserClass getUserWithRoles(Long userId) {
        UserClass user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Hibernate.initialize(user.roles);
        }
        return user;
    }

    public List<UserDTOWithRole> getDataWithRoleData(){
        List<UserDTOWithRole> userDTOWithRoleList = new ArrayList<>();
        for(UserClass user:userRepository.getAllUserWithRoleData()){
            UserDTOWithRole dto = new UserDTOWithRole();
            dto.setId(user.getUserId());
            dto.setUsername(user.getUserName());
            dto.setUserEmail(user.getUserEmail());
            dto.setRoles(new HashSet<>(user.roles));
            userDTOWithRoleList.add(dto);
        }
        return userDTOWithRoleList;
    }

    
}
