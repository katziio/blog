package com.mountblue.blog.dto;

import com.mountblue.blog.model.Comment;
import com.mountblue.blog.model.PostAndTag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PostDto {
    private Long postId;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private Date publishedAt;
    private boolean isPublished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String tags;
    private Set<Comment> comments = new HashSet<>();
    private boolean checkAuthor;
    private Set<PostAndTag> postAndTag = new HashSet<>();
}
