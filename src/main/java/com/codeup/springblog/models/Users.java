package com.codeup.springblog.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class Users {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @Id
    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 200)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    public Users() {
    }
    public Users(String username) {
        this.username = username;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
