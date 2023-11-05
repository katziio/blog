package com.mountblue.blog.repository;

import com.mountblue.blog.model.PostAndTag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostAndTagsRepository extends JpaRepository<PostAndTag, Long> {
    @Query("DELETE FROM PostAndTag pt WHERE pt.post.id = :id")
    @Transactional
    @Modifying
    void deletePostAndTag(long id);
}
