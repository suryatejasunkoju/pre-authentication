package com.roytuts.preauthentication1.controllers;

//import com.roytuts.preauthentication1.entities.User;
//import com.roytuts.preauthentication1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {
//    @Autowired
//    UserRepository userRepository;
    @GetMapping("/")
    public String defaultPage() {
        return "Welcome to Spring Security PreAuthentication";
    }
    @GetMapping("/blogs")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllBlogs() {
        List<String> list=new ArrayList<>();
        list.add("Blog 1");
        list.add("Blog 2");
        list.add("Blog 3");
        list.add("Blog 4");
        return list;
    }


    @GetMapping("/get1")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String get1() {
        return "get1 called";
    }

//    @PostMapping("/user")
//    public User addUser(User user){
//        return userRepository.save(user);
//    }
}
