package com.example.demo.controllers;

import com.example.demo.models.ApplicationUser;
import com.example.demo.models.ApplicationUserRepository;
import com.example.demo.models.Post;
import com.example.demo.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/users")
    public RedirectView createUser(String firstname, String lastname, String username, String password, String bday, String bio) {
        System.out.println(firstname + " " + lastname + " " + username + " " + password);  //Testing
        ApplicationUser newUser = new ApplicationUser(username,
                encoder.encode(password),
                firstname, lastname, bio, bday);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("myprofile");
    }

    @PostMapping("/myprofile")
    public RedirectView showUser(String username, String password) {
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");

    }


    @PostMapping("/users/{id}")
    public RedirectView addPost(@PathVariable long id,  String post) {
        ApplicationUser logged_in_user = applicationUserRepository.findById(id).get();
        Date date = new Date();
        Post new_post = new Post(post, date.getTime(), logged_in_user);
        postRepository.save(new_post);
        return new RedirectView("/myprofile");

    }


    @GetMapping("/myprofile")
    public  String getUser(Principal p, Model m) {
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);


        return "myprofile";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


 }
