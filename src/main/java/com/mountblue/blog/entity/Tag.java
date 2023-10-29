package com.mountblue.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "TAGS")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tag_id;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private List<Post> posts;
    private Date created_at;
    private Date updated_at;
}
