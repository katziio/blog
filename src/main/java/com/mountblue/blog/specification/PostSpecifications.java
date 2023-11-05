package com.mountblue.blog.specification;

import com.mountblue.blog.model.Post;
import com.mountblue.blog.model.PostAndTag;
import com.mountblue.blog.model.Tag;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PostSpecifications {
    public Specification<Post> filterPostsOnAuthors(List<String> authors) {
        return (root, query, criteriaBuilder) ->
                root.get("author").in(authors);
    }

    public Specification<Post> filterPostsOnTags(List<String> tags) {
        return (root, query, criteriaBuilder) -> {
            SetJoin<Object, Object> postAndTagJoin = root.joinSet("postAndTags");
            Join<PostAndTag, Tag> tagJoin = postAndTagJoin.join("tag");
            Predicate[] predicates = tags.stream()
                    .map(tag -> criteriaBuilder.equal(tagJoin.get("name"), tag))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }

    public Specification<Post> filterPostsOnPublishedDate(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("publishedAt"), Date.valueOf(date));
    }

    public Specification<Post> filterPostsOnKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            Predicate authorPredicate = criteriaBuilder.like(root.get("author"), "%" + keyword + "%");
            Predicate titlePredicate = criteriaBuilder.like(root.get("title"), "%" + keyword + "%");
            Predicate contentPredicate = criteriaBuilder.like(root.get("content"), "%" + keyword + "%");
            Predicate excerptPredicate = criteriaBuilder.like(root.get("excerpt"), "%" + keyword + "%");
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<PostAndTag> postAndTagRoot = subquery.from(PostAndTag.class);
            Join<PostAndTag, Tag> tagJoin = postAndTagRoot.join("tag");
            subquery.select(postAndTagRoot.get("post").get("id"))
                    .where(criteriaBuilder.like(tagJoin.get("name"), "%" + keyword + "%"));

            Predicate idPredicate = criteriaBuilder.in(root.get("id")).value(subquery);
            return criteriaBuilder.or(authorPredicate, titlePredicate, contentPredicate, excerptPredicate, idPredicate);
        };
    }
}
