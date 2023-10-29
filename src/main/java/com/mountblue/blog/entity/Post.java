package com.mountblue.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POSTS")
@Data
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
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postId")
    private List<Comment> comments;

}
