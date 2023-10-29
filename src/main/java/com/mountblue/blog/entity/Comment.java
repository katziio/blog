package com.mountblue.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "COMMENTS")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    private Date created_at;
    private Date updated_at;
}
