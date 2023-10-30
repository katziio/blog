package com.mountblue.blog.service;

import com.mountblue.blog.Util.Role;
import com.mountblue.blog.entity.User;
import com.mountblue.blog.model.PostDto;
import com.mountblue.blog.model.UserDto;
import com.mountblue.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto addUser(User user) {
        if(user.getName().equals("Katziio"))
        {
            user.setRole(Role.ADMIN);
        }else {
            user.setRole(Role.AUTHOR);
        }
        try {
            this.userRepository.save(user);
            return new UserDto(user);
        }
        catch (Exception e)
        {
            throw new RuntimeException("user not added");
        }
    }

    public UserDto getUserById(Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isPresent())
        {
            return new UserDto(user.get());
        }else {
            throw new RuntimeException("user not found "+userId);
        }
    }

    public UserDto updateUser(User user) {
        try {
            this.userRepository.save(user);
            return new UserDto(user);
        }
        catch (Exception e)
        {
            throw new RuntimeException("user not updated "+user.getId());
        }
    }

    public UserDto deleteUser(Long userId) {
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isPresent())
        {
            this.userRepository.deleteById(userId);
            return new UserDto(user.get());
        }else {
            throw new RuntimeException("user not found "+userId);
        }
    }
}
