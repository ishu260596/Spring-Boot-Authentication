package com.oauth.auth.controller;

import com.oauth.auth.entity.User;
import com.oauth.auth.service.SendEMailService;
import com.oauth.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

    @Autowired
    private UserService userService;
    @Autowired
    private SendEMailService sendEMailService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(@RequestBody User user) {
        System.out.println(user);
        sendEMailService.sendMail(user.getUsername(), "SignUp");
        return userService.saveUser(user);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Iterable<User> getAll() {
        return userService.getAllUser();
    }

}
