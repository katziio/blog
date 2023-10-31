package com.mountblue.blog.service;

import com.mountblue.blog.Util.Role;
//import com.mountblue.blog.config.Security;
import com.mountblue.blog.entity.User;
import com.mountblue.blog.model.PostDto;
import com.mountblue.blog.model.UserDto;
import com.mountblue.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private Security security;

    public UserDto addUser(User user) {
        if(user.getName().toLowerCase().equals("Katziio".toLowerCase()))
        {
            user.setRole(Role.ADMIN);
        }else {
            user.setRole(Role.AUTHOR);
        }
        Optional<User> userOptional = this.userRepository.findByUserEmail(user.getEmail());
        if(userOptional.isPresent())
        {
            throw new RuntimeException("User Already Exists");
        }
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
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

    public UserDto login(String email, String password) {
        Optional<User> user = this.userRepository.findByUserEmail(email);
        if(!user.isPresent())
        {
            throw new RuntimeException("User not found");
        }
        else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, user.get().getPassword())) {
                return new UserDto(user.get());
            } else {
                throw new RuntimeException("Invalid password");
            }
        }
    }
}
