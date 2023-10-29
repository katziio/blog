package com.mountblue.blog.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String comment;
    private Long post_id;
    @OneToMany(targetEntity = Post.class, mappedBy = "post_id")
    private Post post;
    private Date created_at;
    private Date updated_at;
}
