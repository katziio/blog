package com.mountblue.blog.service.comment;

import com.mountblue.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    public List<Comment> getAllCommentByPostId(Long postId);
    public Comment getCommentByCommentId(Long postId, Long commentId);
    public Comment addComment(Long postId, Comment comment);
    public Comment updateComment(Long postId, Comment comment);
    public boolean deleteComment(Long postId, Long commentId);
}
