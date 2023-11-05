package com.mountblue.blog.restcontroller;

import com.mountblue.blog.dto.PageDto;
import com.mountblue.blog.dto.PostDto;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostRestController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts/save")
    public ResponseEntity<Object> savePost(@RequestBody PostDto postDto) {
        if(postService.getAuth() == null || !postService.getAuth().isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }
        long postId = postService.getSavedPostId(postDto);
        PostDto savedPost = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @GetMapping("/posts")
    public ResponseEntity<Object> viewHomePage(@RequestParam(name = "search", required = false) String search,
                                               @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                               @RequestParam(name = "sort", defaultValue = "id", required = false) String sortBy,
                                               @RequestParam(name = "authors", required = false) List<String> authors,
                                               @RequestParam(name = "tags", required = false) List<String> tags,
                                               @RequestParam(name = "date", required = false) LocalDate date) {
        PageDto pageDto = new PageDto();
        pageDto.setSearch(search);
        pageDto.setPage(page);
        pageDto.setSortBy(sortBy);
        pageDto.setAuthors(authors);
        pageDto.setTags(tags);
        pageDto.setDate(date);

        List<PostDto> list = postService.fetchPosts(pageDto).getBlogs();
        if (list != null) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable long id) {
        PostDto postDto = postService.getPostById(id);
        if (postDto != null) {
            return ResponseEntity.ok(postDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }

    @PutMapping("/posts/{id}/update")
    public ResponseEntity<Object> updatePost(@PathVariable long id,
                                             @RequestBody PostDto postDto) {
        if (!postService.authorize(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized access");
        }
        if (postService.getPostById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        postDto.setPostId(id);
        postService.updatePost(postDto);
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/posts/{id}/delete")
    public ResponseEntity<Object> deletePost(@PathVariable long id) {
        if (!postService.authorize(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorzed access");
        }
        boolean deleted = postService.deletePostById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }
}