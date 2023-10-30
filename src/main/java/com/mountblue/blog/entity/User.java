package com.mountblue.blog.entity;


import com.mountblue.blog.Util.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private Role role;
}
