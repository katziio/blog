package com.mountblue.blog.repository;

import com.mountblue.blog.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    @Query("DELETE FROM Comment c WHERE c.id = :id")
    @Modifying
    @Transactional
    void deleteComment(long id);
}
