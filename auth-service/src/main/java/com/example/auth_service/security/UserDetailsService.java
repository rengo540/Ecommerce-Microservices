package com.example.auth_service.security;

import com.example.auth_service.models.User;
import com.example.auth_service.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =userRepo.findByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException(" user not found")
        );
        return UserAppDetails.buildUserDetails(user);
    }
}
