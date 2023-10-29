package com.mountblue.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JoinColumn(name = "postId")
    private Post postId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated_at;
}
