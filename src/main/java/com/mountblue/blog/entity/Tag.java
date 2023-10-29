package com.mountblue.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "TAGS")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tag_id;
    @ManyToOne(targetEntity = Post.class)
    private Post post;
    private Date created_at;
    private Date updated_at;
}
