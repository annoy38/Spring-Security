package com.security.springsecurityjwt.controller;

import com.security.springsecurityjwt.model.LoginRequest;
import com.security.springsecurityjwt.model.UserClass;
import com.security.springsecurityjwt.model.UserDTOClass;
import com.security.springsecurityjwt.model.UserDTOWithRole;
import com.security.springsecurityjwt.service.UserServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SecurityControllerClass {

    @Autowired
    private UserServiceClass serviceClass;

    @GetMapping("/user/message")
    @PreAuthorize("hasRole('USER')")
    public String printMessage() {
        return "User Api Calling";
    }

    @GetMapping("/user/get-all-data")
//    @PreAuthorize("hasRole('USER')")
    public List<UserDTOClass> fetchAllUserData() {
        return serviceClass.getAllUser();
    }

    @GetMapping("/user/get-all-data-with-role")
//    @PreAuthorize("hasRole('USER')")
    public List<UserDTOWithRole> fetchAllUserDataWithRole() {
        return serviceClass.getDataWithRoleData();
    }

    @GetMapping("/admin/message")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAPI() {
        return "Admin Api Calling";
    }


    @PostMapping("/auth/sign-up")
    public ResponseEntity<String> userSignUp(@RequestBody UserClass userClass) {
        if (serviceClass.isExitUser(userClass.getUserEmail())) {
            return ResponseEntity.ok().body("Email Already Taken!");
        }
        serviceClass.saveUser(userClass);
        return ResponseEntity.ok("Sign Up Successful");
    }

    @PostMapping("/auth/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        return serviceClass.generateToken(loginRequest);
    }

}
