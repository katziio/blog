package com.mountblue.blog.repository;

import com.mountblue.blog.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    @Query("DELETE FROM Post p WHERE p.id = :id")
    @Transactional
    @Modifying
    void deletePost(long id);
}
