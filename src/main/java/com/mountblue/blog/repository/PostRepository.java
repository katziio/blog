package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.model.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT new com.mountblue.blog.model.PostDto(p) FROM Post p")
    List<PostDto> findAllPosts(Pageable pageRequest);

    @Query("SELECT p FROM Post p WHERE p.author IN :authors AND p.published_at IN :publishedDate AND p.tags.name IN :tags")
    List<Post> findPostsByAuthorsAndPublishedDateAndTags(
            @Param("authors") Set<String> authors,
            @Param("publishedDate") Set<LocalDateTime> publishedDate,
            @Param("tags") Set<String> tags
    );
}
