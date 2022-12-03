package com.codeup.springblog.services;

import com.codeup.springblog.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;



    @Value("${spring.mail.from}")
    private String from;

    public void prepareAndSend(Users user, String title, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom(from);
        String subject = "You just submitted a post on Blogbea!";
        msg.setSubject(subject);
        String emailbody = "Your post titled: \"" + title + "\" reads as follows: " + body;
        msg.setText(emailbody);

        try {
            this.emailSender.send(msg);
        } catch (MailException mex) {
            System.err.println(mex.getMessage());
        }
    }

    public void registerSend(Users user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom(from);
         String subject = "Welcome to Blogbea!";
        msg.setSubject(subject);
         String body = "Thank you for signing up to Blogbea, " + user.getUsername() + ". You’ve joined a network of " +
                 "50 million people who are gathering over shared interests. Do more of what you love and find your " +
                 "community along the way. The question is: what are you going to write about first?";
        msg.setText(body);


        try {
            this.emailSender.send(msg);
        } catch (MailException mex) {
            System.err.println(mex.getMessage());
        }
    }
}
