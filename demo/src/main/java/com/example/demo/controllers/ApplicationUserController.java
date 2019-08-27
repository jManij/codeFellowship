package com.example.demo.controllers;

import com.example.demo.models.ApplicationUser;
import com.example.demo.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String firstname, String lastname, String username, String password) {
        System.out.println(firstname + " " + lastname + " " + username + " " + password);  //Testing
        ApplicationUser newUser = new ApplicationUser(username,
                encoder.encode(password),
                firstname, lastname);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("myprofile");
    }

    @PostMapping("/myprofile")
    public RedirectView showUser(String username, String password) {
        //How to Decode???
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");

    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }


 }
