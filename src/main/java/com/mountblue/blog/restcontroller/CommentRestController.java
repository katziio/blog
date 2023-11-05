package com.mountblue.blog.restcontroller;

import com.mountblue.blog.dto.CommentDto;
import com.mountblue.blog.model.Comment;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentRestController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @PostMapping("/posts/{id}/comment/save")
    public ResponseEntity<Object> saveComment(@PathVariable long id,
                                              @RequestBody CommentDto commentDto) {
        if (postService.getPostById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Not Found");
        }
        Comment comment = commentService.saveCommentById(commentDto, id);
        postService.saveCommentInPost(comment, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.convertDaoToDto(comment));
    }

    @PatchMapping("/posts/{postId}/comment/update")
    public ResponseEntity<Object> updateComment(@PathVariable long postId,
                                                @RequestBody CommentDto commentDto) {
        if (!postService.authorize(postId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorzed access");
        } else if (postService.getPostById(postId) == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Not Found");
        }
        CommentDto savedCommentDto = commentService.updateCommentById(commentDto);
        if (savedCommentDto != null) {
            return ResponseEntity.ok(savedCommentDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found");
        }
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}/delete")
    public ResponseEntity<Object> deleteComment(@PathVariable long postId,
                                                @PathVariable long commentId) {
        if (!postService.authorize(postId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorzed access");
        } else if (postService.getPostById(postId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Not Found");
        } else if (commentService.deleteCommentById(commentId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found");
        }
    }
}
