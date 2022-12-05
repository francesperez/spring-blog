package com.codeup.springblog.controllers;

import com.codeup.springblog.config.SecurityConfiguration;
import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.Users;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        return "posts/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/create")
    public String addPage(@ModelAttribute Post post) {
        Users loggedInUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getID() == 0) {
            return "redirect:/login";
        }
        Users user = userDao.findById(loggedInUser.getID());
        post.setUser(user);
        postDao.save(post);
        emailService.prepareAndSend(user, post.getTitle(), post.getBody());
        return "redirect:/posts/show";
    }

    @GetMapping("/show")
    public String showPosts(Model model) {
//        Users user = userDao.findById(loggedInUser.getID());
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/show";
    }

    @GetMapping("/{id}/edit")
    public String showEditPostForm(@PathVariable long id, Model model){
        Users loggedInUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getID() == 0) {
            return "redirect:/login";
        }
        Post post = postDao.findById(id);
        if (post.getUser().getID() != loggedInUser.getID()){
            return "redirect:/posts/show";
        }
        model.addAttribute("post", post);
        return "/posts/edit";
    }


    @PostMapping("/{ID}/edit")
    public String editPost(@ModelAttribute Post post){
        Users loggedInUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getID() == 0) {
            return "redirect:/login";
        }
        Users user = userDao.findById(loggedInUser.getID());
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts/show";
    }

    @PostMapping("/delete")
    public String deletePost(@ModelAttribute Post post){
        Users loggedInUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loggedInUser.getID() == 0) {
            return "redirect:/login";
        }
        Users user = userDao.findById(loggedInUser.getID());
        post.setUser(user);
        postDao.delete(post);
        return "redirect:/posts/show";
    }



}
