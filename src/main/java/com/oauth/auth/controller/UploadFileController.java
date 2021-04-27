package com.oauth.auth.controller;

import com.oauth.auth.entity.User;
import com.oauth.auth.helper.JwtUtil;
import com.oauth.auth.service.SendEMailService;
import com.oauth.auth.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadFileController {

    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private SendEMailService sendEMailService;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/upload"
            , method = RequestMethod.POST
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> UploadFile(@RequestParam("file") MultipartFile file) {

        boolean response = uploadFileService.uploadFile(file);

        if (response == true) {

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();

            System.out.println(username);

            sendEMailService.sendMail(username, "File");
        }
        return new ResponseEntity<>("File upload successfully", HttpStatus.OK);
    }
}
