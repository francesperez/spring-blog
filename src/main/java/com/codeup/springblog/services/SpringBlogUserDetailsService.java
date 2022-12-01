package com.codeup.springblog.services;

import com.codeup.springblog.models.SpringBlogUserDetails;
import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class SpringBlogUserDetailsService implements UserDetailsService {

//This method is adaptable enough to handle many situations.


    public final UserRepository usersDao;

    public SpringBlogUserDetailsService(UserRepository usersDao){
        this.usersDao = usersDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found: " + username);
        } else {
            return new SpringBlogUserDetails(user.getID(), user.getUsername(), user.getEmail(), user.getPassword());
        }
    }
}
