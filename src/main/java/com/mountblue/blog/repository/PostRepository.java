package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.model.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT new com.mountblue.blog.model.PostDto(p) FROM Post")
    List<PostDto> findAllPosts(Pageable pageRequest);
}
