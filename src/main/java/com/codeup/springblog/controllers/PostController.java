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

    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "posts/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/create")
    public String addPage(@ModelAttribute Post post) {
        Users user = userDao.findById(1L);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditPostForm(@PathVariable long id, Model model){
        Post post = postDao.findById(id);
        model.addAttribute("post", post);
        return "/posts/edit";
    }


    @PostMapping("/{ID}/edit")
    public String editPost(@ModelAttribute Post post){
        Users user = userDao.findById(1L);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts/show";
    }

    @GetMapping("/show")
    public String showPosts(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/show";
    }

    @GetMapping("/users")
    public String showUsersForm(Model model) {
        List<Users> users = userDao.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", new Users());
        return "posts/users";
    }

    @PostMapping("/users")
    public String insertUser(@ModelAttribute Users user) {
        userDao.save(user);
        return "redirect:/posts/users";

    }


}
