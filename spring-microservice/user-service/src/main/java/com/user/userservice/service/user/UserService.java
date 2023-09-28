package com.user.userservice.service.user;


import com.user.userservice.model.User;


public interface UserService {

    User findByUsername(String username);

    public User save(User user);

}
