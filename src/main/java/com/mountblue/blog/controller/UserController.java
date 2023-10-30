package com.mountblue.blog.controller;

import com.mountblue.blog.entity.User;
import com.mountblue.blog.model.UserDto;
import com.mountblue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserDto signUp(User user)
    {
        return this.userService.addUser(user);
    }

    @GetMapping("/login")
    public UserDto login(@RequestParam String email,@RequestParam String password)
    {
        return this.userService.login(email,password);
    }

    @GetMapping("/get/{userId}")
    public UserDto getUser(Long userId)
    {
        return this.userService.getUserById(userId);
    }

    @PutMapping("/update")
    public UserDto updateUser(User user)
    {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/delete/{userId}")
    public UserDto deleteUser(Long userId)
    {
        return this.userService.deleteUser(userId);
    }

}
