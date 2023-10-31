package com.mountblue.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "POSTS")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private boolean isPublished;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime published_at;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postId")
    private List<Comment> comments;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST
    })
    private List<Tag> tags;
}
