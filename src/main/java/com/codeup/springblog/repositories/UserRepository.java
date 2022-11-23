package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String>{
    Users findByUsername(String username);
}
