package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserRepository usersDao;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository usersDao, PasswordEncoder passwordEncoder, EmailService emailService){
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/posts/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new Users());
        return "/posts/registration";
    }

    @PostMapping("/posts/register")
    public String registerUser(@ModelAttribute Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        emailService.registerSend(user);
        return "redirect:/login";
    }


}
