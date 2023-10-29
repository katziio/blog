package com.mountblue.blog.controller;

import com.mountblue.blog.model.PostDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @PostMapping("/add")
    public PostDto addPost(){
        return null;
    }

    @PutMapping("/update")
    public PostDto updatePost(){
        return null;
    }

    @DeleteMapping("/delete/{postId}")
    public PostDto deletePost(@PathVariable Long postId){
        return null;
    }

    @GetMapping("/get")
    public List<PostDto> getPostList(){
        return null;
    }

    @GetMapping("/get/{postId}")
    public PostDto getPost(@PathVariable  Long postId)
    {
        return null;
    }

    @GetMapping()
    public List<PostDto> getPostListBySort(@RequestParam String searchField, @RequestParam String order){
        return null;
    }
}
