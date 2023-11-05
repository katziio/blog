package com.mountblue.blog.restcontroller;

import com.mountblue.blog.dto.UserDto;
import com.mountblue.blog.service.JwtService;
import com.mountblue.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup/user")
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {
        try {
            userService.addUser(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User exists with the email");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Succesfully added user");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(jwtService.generateToken(userDto.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }
}

