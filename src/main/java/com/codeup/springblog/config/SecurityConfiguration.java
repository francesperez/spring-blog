package com.codeup.springblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/posts/create", "/posts/{id}/edit").authenticated()
                .antMatchers(",", "/posts/register", "/posts", "/posts/{id}").permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/posts/index")
                .and().logout()
                .and().httpBasic();
        return http.build();
    }

//We have reweritten the application so that the login page is leveraging the usersDao. So now Spring security
// understands that this is the default behavior: that the default code has been overridden and the load
// userbyusername is now using the usersDao, which is loading information from the database and checking to see if
// there is a matching username when you are logging in.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
