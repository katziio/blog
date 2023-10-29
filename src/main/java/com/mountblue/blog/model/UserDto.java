package com.mountblue.blog.model;

import com.mountblue.blog.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDto(User user)
    {
        this.id = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
