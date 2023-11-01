package com.mountblue.blog.service.auth;

import com.mountblue.blog.entity.User;
import com.mountblue.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent())
        {
            CustomUserDetails customUserDetails = new CustomUserDetails();
            customUserDetails.setUser(user.get());
            return customUserDetails;
        }else {
            throw new RuntimeException("User Not found");
        }
    }
}
