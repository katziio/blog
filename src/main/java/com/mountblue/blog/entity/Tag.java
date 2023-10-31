package com.mountblue.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private List<Post> posts;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated_at;
}
