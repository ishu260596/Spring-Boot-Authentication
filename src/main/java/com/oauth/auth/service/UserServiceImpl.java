package com.oauth.auth.service;

import com.oauth.auth.entity.User;
import com.oauth.auth.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public String saveUser(User user) {
        userDAO.save(user);
        return "Signup successfully";
    }

    @Override
    public void resetPassword(User user) {
        String email = user.getUsername();
        User findUser = userDAO.findUserByName(email);
        user.setId(findUser.getId());
        userDAO.save(user);
    }

    @Override
    public String logIn(User user) {
        return null;
    }

    @Override
    public Iterable<User> getAllUser() {
        return userDAO.findAll();
    }

}
