package com.oauth.auth.service;

import com.oauth.auth.entity.User;
import com.oauth.auth.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEMailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserDAO userDAO;

    String subject;
    String message;

    public String sendMail(String user, String type) {

        String subject;
        String message;

        switch (type) {

            case "LogIn":

                subject = "LoggedIn Successfully";
                message = "congrats ,Now you are logged into the system";
                String email = user;
                User findUser = userDAO.findUserByName(email);

                if (userDAO.existsById(findUser.getId())) {
                    return sendEmail(subject, message, user);
                } else return "You are not the user bro!";

            case "SignUp":

                subject = "SignUp Successfully";
                message = "congrats ,Now you are the member of our tribe";
                return sendEmail(subject, message, user);

            case "SendingMail":

                subject = "Sending Mail Successfully";
                message = "congrats ,You are receiving my email";
                return sendEmail(subject, message, user);

            case "File":
                subject = "Uploaded Successfully";
                message = "congrats ,You uploaded a file successfully";
                return sendEmail(subject, message, user);

            case "WrongPassword":
                subject = "Reset password";
                message = "somebody tried to access your account with wrong password";
                return sendEmail(subject, message, user);

            case "PasswordChange":
                subject = "Password changed successfully";
                message = "You just changed tour password";
                return sendEmail(subject, message, user);

        }

        return "send unsuccessful";

    }

    private String sendEmail(String subject, String message, String user) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("kumarishwar623@gmail.com");
        simpleMailMessage.setTo(user);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);

        return "Send Successful";
    }
}
