package com.mountblue.blog.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private Date published_at;
    private boolean is_published;
    private Date created_at;
    private Date updated_at;
}