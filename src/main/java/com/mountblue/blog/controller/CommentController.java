package com.mountblue.blog.controller;

import com.mountblue.blog.dto.CommentDto;
import com.mountblue.blog.model.Comment;
import com.mountblue.blog.service.CommentService;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @GetMapping("/posts/{id}/comment/new")
    public String newComment(Model model,
                             @PathVariable long id) {
        model.addAttribute("id", id);
        return "comment";
    }

    @PostMapping("/posts/{id}/comment/save")
    public String saveComment(@PathVariable long id,
                              @ModelAttribute CommentDto commentDto) {
        Comment comment = commentService.saveCommentById(commentDto, id);
        postService.saveCommentInPost(comment, id);
        return "redirect:/posts/" + id;
    }

    @PreAuthorize("(hasRole('AUTHOR') and @postService.checkAuthor(#postId)) or hasRole('ADMIN')")
    @GetMapping("/posts/{postId}/comment/{commentId}/edit")
    public String editComment(@PathVariable long postId,
                              @PathVariable long commentId,
                              Model model) {
        model.addAttribute("id", postId);
        model.addAttribute("comment", commentService.getCommentById(commentId));
        return "comment";
    }

    @PreAuthorize("(hasRole('AUTHOR') and @postService.checkAuthor(#postId)) or hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comment/update")
    public String updateComment(@PathVariable long postId,
                                @ModelAttribute CommentDto commentDto) {
        commentService.updateCommentById(commentDto);
        return "redirect:/posts/" + postId;
    }

    @PreAuthorize("(hasRole('AUTHOR') and @postService.checkAuthor(#postId)) or hasRole('ADMIN')")
    @PostMapping("/posts/{postId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable long postId,
                                @PathVariable long commentId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/posts/" + postId;
    }
}
