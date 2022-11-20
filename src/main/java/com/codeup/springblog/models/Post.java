package com.codeup.springblog.models;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 3000)
    private String body;

    public Post() {
    }

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post(long ID, String title, String body) {
        this.ID = ID;
        this.title = title;
        this.body = body;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
