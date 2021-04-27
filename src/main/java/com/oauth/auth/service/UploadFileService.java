package com.oauth.auth.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Service
public class UploadFileService {

    public boolean uploadFile(MultipartFile file) {
        try {
            file.transferTo(new File("E:\\" +
                    file.getOriginalFilename()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
