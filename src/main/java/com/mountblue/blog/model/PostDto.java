package com.mountblue.blog.model;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private LocalDateTime published_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private boolean isPublished;
    private List<Comment> commentList;

    public PostDto(Post post)
    {
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.id = post.getId();
        this.created_at = post.getCreatedAt();
        this.excerpt = post.getExcerpt();
        this.published_at = post.getPublishedAt();
        this.updated_at = post.getUpdatedAt();
        this.title = post.getTitle();
        this.isPublished = post.isPublished();
        this.commentList = post.getComments();
    }
}
