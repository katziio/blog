package com.mountblue.blog.controller;

import com.mountblue.blog.dto.PageDto;
import com.mountblue.blog.dto.PostDto;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home() {
        return "redirect:/posts";
    }

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute PostDto postDto) {
        long postId = postService.getSavedPostId(postDto);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts")
    public String viewHomePage(@RequestParam(name = "search", required = false) String search,
                               @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                               @RequestParam(name = "sort", defaultValue = "id", required = false) String sortBy,
                               @RequestParam(name = "authors", required = false) List<String> authors,
                               @RequestParam(name = "tags", required = false) List<String> tags,
                               @RequestParam(name = "date", required = false) LocalDate date,
                               Model model) {
        PageDto pageDto = new PageDto();
        pageDto.setSearch(search);
        pageDto.setPage(page);
        pageDto.setSortBy(sortBy);
        pageDto.setAuthors(authors);
        pageDto.setTags(tags);
        pageDto.setDate(date);

        model.addAttribute("posts", postService.fetchPosts(pageDto));
        return "index";
    }

    @PreAuthorize("hasAnyRole('AUTHOR','ADMIN')")
    @GetMapping("/posts/new")
    public String newPost() {
        return "newpost";
    }


    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable long id, Model model) {
        model.addAttribute("blog", postService.getPostById(id));
        return "blog";
    }

    @PreAuthorize("(hasRole('AUTHOR') and @postService.checkAuthor(#id)) or hasRole('ADMIN')")
    @PostMapping("/posts/{id}/update")
    public String updateEditedPost(@PathVariable long id,
                                   @ModelAttribute PostDto postDto) {
        postDto.setPostId(id);
        postService.updatePost(postDto);
        return "redirect:/posts/" + id;
    }

    @PreAuthorize("(hasRole('AUTHOR') and @postService.checkAuthor(#id)) or hasRole('ADMIN')")
    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable int id, Model model) {
        model.addAttribute("blog", postService.getPostById(id));
        return "newpost";
    }

    @PreAuthorize("(hasRole('AUTHOR') and @postService.checkAuthor(#id)) or hasRole('ADMIN')")
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return "redirect:/posts";
    }
}
