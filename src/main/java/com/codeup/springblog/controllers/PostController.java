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

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }


    @GetMapping("/index")
    public String indexPage(){
        return "posts/index";
    }

//    @GetMapping("/{post}")
//    public String showPage(@PathVariable String post, Model model){
//        Post post1 = new Post(post, "This is my very first post! Do you like it?");
//        Post post2 = new Post(post, "This is my second post! Do you like it?");
//        post1.setID(1);
//        post2.setID(2);
//        List<Post> posts = new ArrayList<>(List.of(post1, post2));
//        model.addAttribute("posts", posts);
//        return "posts/show";
//    }



    @GetMapping("/{id}")
    public String postPage(@PathVariable long id){
        return "Post " + id + " would eventually show up here!";
    }

    @GetMapping("/create")
    public String createPage(){
        return "posts/create";
    }

    @PostMapping(path ="/create")
    public String addPage(@RequestParam(name ="title") String title, @RequestParam(name="body") String body){
        Post post = new Post(title, body);
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
