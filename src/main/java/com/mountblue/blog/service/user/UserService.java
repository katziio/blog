package com.mountblue.blog.service.user;

import com.mountblue.blog.entity.User;
import com.mountblue.blog.model.UserDto;

public interface UserService {
    public UserDto addUser(User user);
    public UserDto getUserById(Long userId);
    public UserDto updateUser(User user);
    public UserDto deleteUser(Long userId);
    public UserDto login(String email, String password);
}
