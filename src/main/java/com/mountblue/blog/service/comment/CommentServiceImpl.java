package com.mountblue.blog.service.comment;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.exception.DataNotFoundException;
import com.mountblue.blog.repository.CommentRepository;
import com.mountblue.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public List<Comment> getAllCommentByPostId(Long postId) {
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if (postOptional.isPresent()) {
            System.out.println(postOptional.get().getComments().size());
            System.out.println(postOptional.get().toString());
            return postOptional.get().getComments();
        } else {
            throw new DataNotFoundException("Post not found");
        }
    }

    public Comment getCommentByCommentId(Long postId, Long commentId) {
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if (postOptional.isPresent()) {
            for (Comment comment : postOptional.get().getComments()) {
                if (comment.getId() == commentId) {
                    return comment;
                }
            }
            throw new DataNotFoundException("Comment Not found");
        } else {
            throw new DataNotFoundException("Post not found");
        }
    }

    public Comment addComment(Long postId, Comment comment) {
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if (postOptional.isPresent()) {
            try {
                comment.setPostId(postOptional.get());
                comment.setCreatedAt(LocalDateTime.now());
                Comment commentDb = this.commentRepository.save(comment);
                return commentDb;
            } catch (Exception e) {
                throw new DataNotFoundException(e.getMessage());
            }
        } else {
            throw new DataNotFoundException("Post not found");
        }
    }

    public Comment updateComment(Long postId, Comment comment) {
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Optional<Comment> commentOptional = this.commentRepository.findById(comment.getId());
            if (commentOptional.isPresent()) {
                try {
                    comment.setUpdatedAt(LocalDateTime.now());
                    Comment commentDb = this.commentRepository.save(comment);
                    return commentDb;
                } catch (Exception e) {
                    throw new DataNotFoundException("Comment not added"+ e.getMessage());
                }
            } else {
                throw new DataNotFoundException("Comment not found");
            }

        } else {
            throw new DataNotFoundException("Post not found");
        }
    }

    public boolean deleteComment(Long postId, Long commentId) {
        Optional<Post> postOptional = this.postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Optional<Comment> commentOptional = this.commentRepository.findById(commentId);
            if (commentOptional.isPresent()) {
                try {
                    this.commentRepository.deleteById(commentId);
                    return true;
                } catch (Exception e) {
                    throw new DataNotFoundException("Comment not added"+ e.getMessage());
                }
            } else {
                throw new DataNotFoundException("Comment not found");
            }

        } else {
            throw new DataNotFoundException("Post not found");
        }
    }
}
