package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    @GetMapping
    @ResponseBody
    public String indexPage(){
        return "posts index page";
    }

    @GetMapping("/{id}")
    @ResponseBody
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
