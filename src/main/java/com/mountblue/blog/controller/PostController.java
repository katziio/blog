package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.model.PostDto;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/add")
    public PostDto addPost(@RequestBody Post post){

        return this.postService.addPost(post);
    }

    @PutMapping("/update")
    public PostDto updatePost(@RequestBody Post post){
        return this.postService.updatePost(post);
    }

    @DeleteMapping("/delete/{postId}")
    public PostDto deletePost(@PathVariable Long postId){
       return this.postService.deletePost(postId);
    }

    @GetMapping("/get")
    public List<PostDto> getPostList(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "author") String sortBy,
                                     @RequestParam(defaultValue = "asc") String orderBy){
       return this.postService.getPostLists(page,size,sortBy,orderBy);
    }

    @GetMapping("/get/{postId}")
    public PostDto getPost(@PathVariable  Long postId)
    {
        return this.postService.findPostById(postId);
    }

//    @GetMapping()
//    public List<PostDto> getPostListBySort(@RequestParam String searchField, @RequestParam String order,
//                                           @RequestParam(defaultValue = "0") int page,
//                                           @RequestParam(defaultValue = "10") int size){
//        return null;
//    }
}
