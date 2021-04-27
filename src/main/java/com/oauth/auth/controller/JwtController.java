package com.oauth.auth.controller;


import com.oauth.auth.entity.User;
import com.oauth.auth.helper.JwtUtil;
import com.oauth.auth.model.JwtResponse;
import com.oauth.auth.service.CustomerUserDetailService;
import com.oauth.auth.service.SendEMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerUserDetailService customerUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SendEMailService sendEMailService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> generatedToken(@RequestBody User user) throws Exception {
        System.out.println(user);
        try {
            authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword()));

        } catch (Exception e) {
            e.printStackTrace();
            sendEMailService.sendMail(user.getUsername(), "WrongPassword");
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails = this.customerUserDetailService.loadUserByUsername(user.getUsername());

        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println(token);
        sendEMailService.sendMail(user.getUsername(), "LogIn");
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
