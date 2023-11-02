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

//    @Query("SELECT p FROM Post p WHERE p.author IN :authors AND p.published_at IN :publishedDate AND p.tags.name IN :tags")
//    List<Post> findPostsByAuthorsAndPublishedDateAndTags(
//            @Param("authors") Set<String> authors,
//            @Param("publishedDate") Set<LocalDateTime> publishedDate,
//            @Param("tags") Set<String> tags
//    );

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

    @Query("SELECT p FROM Post p WHERE p.tags IN (SELECT t FROM Tag t WHERE t.name IN :tagNames)")
    List<Post> findPostsByTagNames(@Param("tagNames") List<String> tagNames);

    @Query("SELECT p FROM Post p WHERE p.author IN :authorList")
    List<Post> findPostsByAuthorNames(@Param("authorList") String[] authorList);

    @Query("SELECT p FROM Post p WHERE CONCAT(p.title,' ', p.content,' ', p.author) LIKE %?1%")
    public List<Post> searchByKeyword(String keyword);

//    @Query("select post from Post post join post.tags tag " +
//            "where post.isPublished = true and " +
//            "tag.name in :tags and " +
//            "( upper(post.title) like concat('%', upper(:search), '%') " +
//            "or upper(post.content) like concat('%', upper(:search), '%') " +
//            "or upper(post.author) like concat('%', upper(:search), '%') " +
//            ") group by  post.id")
//    List<Post> getByTagsAndSearch(@Param("tags") String[] tags, @Param("search") String search, Pageable pageable);

}
