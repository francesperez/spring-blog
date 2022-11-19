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

//    @GetMapping("/{id}")
//    public String postPage(@PathVariable long id){
//        return "Post " + id + " would eventually show up here!";
//    }

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
