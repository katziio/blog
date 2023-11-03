package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.exception.DataNotFoundException;
import com.mountblue.blog.model.PostDto;
import com.mountblue.blog.service.post.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

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

    @GetMapping("/get/filter")
    public List<PostDto> getPostByTagNames(@RequestParam String[] tagsNames){
        return this.postService.filterByTagsNames(tagsNames);
    }
    @GetMapping("/get/filter/byAuthor")
    public List<PostDto> getPostByAuthor(@RequestParam String[] authorNames){
        return this.postService.filterByAuthor(authorNames);
    }

    @GetMapping("/get/filter/byDate")
    public List<PostDto> getPostByAuthor(@RequestParam LocalDateTime[] date){
        return this.postService.filterByDate(date);
    }

    @GetMapping("/get/filter/date")
    public Set<LocalDateTime> getDateForFilter()
    {
        return this.postService.getDateList();
    }

    @GetMapping("/get/filter/author")
    public Set<String> getAuthorForFilter()
    {
        return this.postService.getAuthorNameList();
    }

    @GetMapping("/get/filter/tags")
    public Set<String> getTagForFilter()
    {
        return this.postService.getTagNameList();
    }


    @GetMapping("/search/{keyword}")
    public List<PostDto> findByKeyword(@PathVariable String keyword)
    {
            return this.postService.findByKeyword(keyword);
    }

}
