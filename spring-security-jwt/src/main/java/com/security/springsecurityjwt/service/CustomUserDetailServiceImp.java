package com.security.springsecurityjwt.service;

import com.security.springsecurityjwt.model.UserClass;
import com.security.springsecurityjwt.repository.IRoleRepository;
import com.security.springsecurityjwt.repository.IUserRepository;
import com.security.springsecurityjwt.repository.UserRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class CustomUserDetailServiceImp implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserClass> user = userRepository.fetchUserByEmail(email);
//        user = Optional.of(serviceClass.getUserWithRoles(user.get().getUserId()));
//        user.get().setRoles(serviceClass.getUserWithRoles(user.get().getUserId()).getRoles());
//        if (!user.isPresent() || user.get().roles.isEmpty()) {
//            throw new UsernameNotFoundException(email);
//        }

        return new CustomUserDetail(user);
    }
}
