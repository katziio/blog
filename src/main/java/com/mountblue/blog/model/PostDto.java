package com.mountblue.blog.model;

import com.mountblue.blog.entity.Post;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String author;
    private Date published_at;
    private boolean is_published;
    private Date created_at;
    private Date updated_at;

    public PostDto(Post post)
    {
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.id = post.getId();
        this.created_at = post.getCreated_at();
        this.excerpt = post.getExcerpt();
        this.published_at = post.getPublished_at();
        this.updated_at = post.getUpdated_at();
        this.title = post.getTitle();
        this.is_published = post.is_published();
    }
}
