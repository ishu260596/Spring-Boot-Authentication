package com.oauth.auth.service;

import com.oauth.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {


    String saveUser(User user);

    void resetPassword(User user);

    String logIn(User user);

    Iterable<User> getAllUser();
}
