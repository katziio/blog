package com.mountblue.blog.controller;

import com.mountblue.blog.dto.UserDto;
import com.mountblue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup/user")
    public String addUser(@ModelAttribute UserDto userDto, Model model) {
        try {
            userService.addUser(userDto);
        } catch (Exception e) {
            model.addAttribute("exists", "An account for that email already exists");
            return "signup";
        }
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/accessdenied")
    public String accessDenied() {
        return "accessdenied";
    }
}
