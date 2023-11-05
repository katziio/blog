package com.mountblue.blog.service;

import com.mountblue.blog.model.User;
import com.mountblue.blog.security.UserInfoUserDetails;
import com.mountblue.blog.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userInfoRepository.findByEmail(username);
        return user.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
    }
}
