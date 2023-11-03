package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.model.PostDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT new com.mountblue.blog.model.PostDto(p) FROM Post p")
    List<PostDto> findAllPosts(Pageable pageRequest);


    @Query("SELECT new com.mountblue.blog.model.PostDto(p) FROM Post p")
    List<PostDto> findAllPosts();

    @Query("select post from Post post join post.tags tag " +
            "where (post.isPublished = true) and " +
            "upper(tag.name) like concat('%', upper(?1), '%') " +
            "or upper(post.title) like concat('%', upper(?1), '%') " +
            "or upper(post.content) like concat('%', upper(?1), '%') " +
            "or upper(post.author) like concat('%', upper(?1), '%') group by  post.id")
    List<Post> getBySearch(String search, Pageable pageable);

    @Query("select post from Post post join post.tags tag " +
            "where post.isPublished = true and " +
            "post.isPublished = :isPublished and " +
            "post.author in :authorNames and " +
            "tag.name in :tagNames and " +
            "( upper(post.title) like concat('%', upper(:search), '%') " +
            "or upper(post.content) like concat('%', upper(:search), '%') " +
            "or upper(post.author) like concat('%', upper(:search), '%') " +
            ") group by  post.id")
    List<Post> getPostsByAllParams(@Param("isPublished") boolean isPublished,
                                   @Param("search") String search,
                                   @Param("authorNames") String[] authorNames,
                                   @Param("tagNames") String[] tagNames, Pageable pageable);

    @Query("select post from Post post " +
            "where post.isPublished = true and " +
            "post.author in :authors and " +
            "( upper(post.title) like concat('%', upper(:search), '%') " +
            "or upper(post.content) like concat('%', upper(:search), '%') " +
            "or upper(post.author) like concat('%', upper(:search), '%') " +
            ") group by  post.id")
    List<Post> getByAuthorsAndSearch(@Param("authors") String[] authors, @Param("search") String search, Pageable pageable);

    @Query("select new com.mountblue.blog.model.PostDto(p) from Post p join p.tags tag where p.isPublished=true and tag.name in :tags")
    List<PostDto> findPostsByTagNames(@Param("tags") String[] tags);

    @Query("select new com.mountblue.blog.model.PostDto(p) from Post p WHERE p.author in :authors")
    List<PostDto> getByAuthors(@Param("authors") String[] authors);

    @Query("SELECT new com.mountblue.blog.model.PostDto(p) FROM Post p JOIN p.tags t WHERE CONCAT(p.title, ' ', p.content, ' ', p.author) LIKE %:keyword%")
    public List<PostDto> searchByKeyword(@Param("keyword") String keyword);

    @Query("select new com.mountblue.blog.model.PostDto(p) from Post p WHERE p.createdAt IN :date")
    List<PostDto> findByDate(@Param("date") LocalDateTime[] date);
}
