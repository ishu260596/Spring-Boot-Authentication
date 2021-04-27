package com.oauth.auth.service;

import com.oauth.auth.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.oauth.auth.entity.User user = userDAO.findUserByName(username);
        System.out.println(user);

        if (user.getUsername().equals(username)) {

            return new User(user.getUsername(),
                    user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("user not found");

        }

        /*

        if (username.equals("Ishwar")) {
            return new User("Ishwar", "password", new ArrayList<>());

        } else {
            throw new UsernameNotFoundException("user not found");
        }
        */
    }


}
