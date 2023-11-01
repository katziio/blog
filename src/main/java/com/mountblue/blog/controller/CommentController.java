package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.service.comment.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/{postId}/getComments")
    public List<Comment> getAllComments(@PathVariable Long postId) {
        return this.commentService.getAllCommentByPostId(postId);
    }

    @GetMapping("/{postId}/get/{commentId}")
    public Comment getComments(@PathVariable Long postId, @PathVariable Long commentId) {
        return this.commentService.getCommentByCommentId(postId, commentId);
    }

    @PostMapping("/{postId}/add")
    public Comment addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        return this.commentService.addComment(postId, comment);
    }

    @PutMapping("/{postId}/update/{commentId}")
    public Comment updateComment(@PathVariable Long postId, @RequestBody Comment comment) {
        return this.commentService.updateComment(postId, comment);
    }

    @DeleteMapping("/{postId}/delete/{commentId}")
    public boolean deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return this.commentService.deleteComment(postId, commentId);
    }
}
