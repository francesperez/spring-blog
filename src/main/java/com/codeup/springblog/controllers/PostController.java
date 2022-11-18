package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/index")
    public String indexPage(){
        return "posts/index";
    }

    @GetMapping("/{post}")
    public String showPage(@PathVariable String post, Model model){
        Post post1 = new Post(post, "This is my very first post! Do you like it?");
        Post post2 = new Post(post, "This is my second post! Do you like it?");
        post1.setID(1);
        post2.setID(2);
        List<Post> posts = new ArrayList<>(List.of(post1, post2));
        model.addAttribute("posts", posts);
        return "posts/show";

    }

    @GetMapping("/{id}")
    public String postPage(@PathVariable long id){
        return "Post " + id + " would eventually show up here!";
    }

    @GetMapping("/create")
    @ResponseBody
    public String createPage(){
        return "A form will eventually be here! Stay tuned...";
    }

    @PostMapping(path ="/create")
    @ResponseBody
    public String postPage(){
        return "Create a new post here:";
    }




}
