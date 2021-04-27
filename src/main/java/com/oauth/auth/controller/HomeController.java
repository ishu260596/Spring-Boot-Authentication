package com.oauth.auth.controller;


import com.oauth.auth.entity.User;
import com.oauth.auth.service.SendEMailService;
import com.oauth.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private SendEMailService sendEMailService;

    @PutMapping("/resetPassword")
    public void resetPassword(@RequestBody User user) {
        sendEMailService.sendMail(user.getUsername(), "PasswordChange");
        userService.resetPassword(user);
    }

    @GetMapping("/sendmail")
    public String sendEMail() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        System.out.println(username);

        return sendEMailService.sendMail(username, "SendingMail");
    }

    @GetMapping("/getAllusers")
    public Iterable<User> getAllUser() {
        return userService.getAllUser();
    }

}
