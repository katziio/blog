package com.mountblue.blog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
public class PageDto {
    private List<PostDto> blogs;
    private List<String> tags;
    private List<String> authors;
    private List<String> totalTags;
    private Set<String> totalAuthors;
    private LocalDate date;
    private int page;
    private int totalPages;
    private String sortBy;
    private String search;
}
