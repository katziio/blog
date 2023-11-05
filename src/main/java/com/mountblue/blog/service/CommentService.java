package com.mountblue.blog.service;

import com.mountblue.blog.dto.CommentDto;
import com.mountblue.blog.model.Comment;
import com.mountblue.blog.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    public CommentsRepository commentsRepository;

    public Comment saveCommentById(CommentDto commentDto, long id) {
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setComment(commentDto.getComment());
        return commentsRepository.save(comment);
    }

    public CommentDto updateCommentById(CommentDto commentDto) {
        long id = commentDto.getCommentId();
        Comment comment = commentsRepository.findById(id).orElse(null);
        if (comment == null) return null;
        comment.setComment(commentDto.getComment());
        return convertDaoToDto(commentsRepository.save(comment));
    }

    public boolean deleteCommentById(long commentId) {
        if (commentsRepository.findById(commentId).isEmpty())
            return false;
        commentsRepository.deleteComment(commentId);
        return true;
    }

    public CommentDto getCommentById(long id) {
        CommentDto commentDto = new CommentDto();
        try {
            Comment comment = commentsRepository.findById(id).orElse(null);
            commentDto.setCommentId(comment.getId());
            commentDto.setComment(comment.getComment());
        } catch (Exception e) {
            return null;
        }
        return commentDto;
    }

    public CommentDto convertDaoToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getId());
        commentDto.setComment(comment.getComment());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }
}
