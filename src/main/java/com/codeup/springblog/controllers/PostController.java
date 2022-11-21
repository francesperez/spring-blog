package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

//    Here we are declaring a post repo.
    private final PostRepository postDao;

//Here is the posts constructor. This is the dependency injection.
    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }


    @GetMapping("/index")
    public String indexPage(){
        return "posts/index";
    }

    @GetMapping("/create")
    public String createPage(){
        return "posts/create";
    }

    @PostMapping(path ="/create")
//Model layer gets created
    public String addPage(@RequestParam(name ="title") String title, @RequestParam(name="body") String body){
//Post request becomes a post object.
        Post post = new Post(title, body);
//An instantiation of the post model comes into existence. It has object relational mapping to the table that was
// created into the database.

//This is the data access layer. The post dao received the post object, and sends a bunch of my sql commends suitable
// for insertion into the table that is mapped. It sends actual MySQL commands to the database.
        postDao.save(post);
        return"redirect:/posts/show";
    }

    @GetMapping("/show")
    public String showPosts(Model model){
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/show";
    }



}
