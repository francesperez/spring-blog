package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
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
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/index")
    public String indexPage(){
        return "posts/index";
    }

    @GetMapping("/create")
    public String createPage(Model model){
        List<Users> users = userDao.findAll();
        model.addAttribute("users", users);
        return "posts/create";
    }

    @PostMapping(path ="/create")
//Model layer gets created
    public String addPage(@RequestParam(name ="title") String title, @RequestParam(name="body") String body,
                          @RequestParam(name="user") long ID){
//Post request becomes a post object.
        Users user = userDao.findById(ID);
        Post post = new Post(title, body, user);
        postDao.save(post);
        return"redirect:/posts/show";
    }

    @GetMapping("/show")
    public String showPosts(Model model, Model model2){
        List<Post> posts = postDao.findAll();
        List<Users> users = userDao.findAll();
        model2.addAttribute("users", users);
        model.addAttribute("posts", posts);
        return "posts/show";
    }

    @GetMapping("/users")
    public String showUsersForm(Model model){
        List<Users> users = userDao.findAll();
        model.addAttribute("users", users);
        return "posts/users";
    }

    @PostMapping("/users")
    public String insertUser(@RequestParam(name = "name") String name,@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password ){
        Users user = new Users(name, email, password);
        userDao.save(user);
        return "redirect:/posts/users";

    }



}
