package com.user.userservice.security;

import com.user.userservice.model.Role;
import com.user.userservice.model.User;
import com.user.userservice.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        Role role = user.getRole();
        System.out.println("User from Service : "+user.getUsername());
        Hibernate.initialize(user.getRole().getRole());
        System.out.println("Roles from Service : "+user.getRole().getRole());
        return new CustomUserDetails(user);
    }
}
