package com.mountblue.blog.service.post;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import com.mountblue.blog.exception.DataNotFoundException;
import com.mountblue.blog.exception.ServerException;
import com.mountblue.blog.model.PostDto;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public PostDto addPost(Post post) {
        try {
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setPublishedAt(LocalDateTime.now());
            if (post.getTags() != null) {
                List<Tag> tagList = this.findOrCreateTaglist(post.getTags());
                post.setTags(tagList);
            }
            this.postRepository.save(post);
            return new PostDto(post);
        } catch (Exception e) {
            throw new ServerException("post not added" + e.getMessage());
        }
    }

    @Override
    public PostDto updatePost(Post post) {
        try {
            post.setUpdatedAt(LocalDateTime.now());
            this.postRepository.save(post);
            return new PostDto(post);
        } catch (Exception e) {
            throw new ServerException("Post not Updated" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public PostDto deletePost(Long postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        if (post.isPresent()) {
            this.postRepository.delete(post.get());
            return new PostDto(post.get());
        } else {
            throw new ServerException("post not deleted");
        }
    }

    public List<PostDto> getPostLists(int page, int size, String sortBy, String orderBy) {
        Pageable pageable = this.pageable(page, size, sortBy, orderBy);
        return this.postRepository.findAllPosts(pageable);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        if (post.isPresent()) {
            return new PostDto(post.get());
        } else {
            throw new ServerException("Post not found for id" + postId);
        }
    }

    public Pageable pageable(int page, int size, String sortField, String orderBy) {
        Map<String, String> sortFieldMapping = new HashMap<>();
        sortFieldMapping.put("title", "title");
        sortFieldMapping.put("author", "author");
        sortFieldMapping.put("published", "publishedAt");
        sortFieldMapping.put("created", "createdAt");
        sortFieldMapping.put("updated", "updatedAt");
        String sortedField = sortFieldMapping.get(sortField);
        if (sortedField == null) {
            sortedField = "author";
        }
        Sort sort = null;
        if (orderBy.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(sortedField));
        } else {
            sort = Sort.by(Sort.Order.asc(sortedField));
        }
        return PageRequest.of(page, size, sort);
    }

    @Override
    public List<Post> filterByTagsNames(String[] tags) {
        try {
            System.out.println(tags.length);
            return this.postRepository.findPostsByTagNames(tags);
        } catch (Exception e) {
            throw new DataNotFoundException(e.getLocalizedMessage());
        }
    }

    public List<Post> filterByAuthor(String[] authorList) {
        try {
            List<Post> posts = this.postRepository.getByAuthors(authorList);
            return posts;
        } catch (Exception e) {
            throw new DataNotFoundException(e.getLocalizedMessage());
        }
    }

    public Set<String> getTagNameList() {
        List<Tag> tags = this.tagRepository.findAll();
        Set<String> tagList = new HashSet<>();
        for (Tag tag : tags) {
            tagList.add(tag.getName());
        }
        System.out.println(tagList.size());
        return tagList;
    }

    public Set<String> getAuthorNameList() {
        List<Post> posts = this.postRepository.findAll();
        Set<String> authorNameList = new HashSet<>();
        for (Post post : posts) {
            authorNameList.add(post.getAuthor());
        }
        return authorNameList;
    }

    public Set<LocalDateTime> getDateList() {
        List<Post> posts = this.postRepository.findAll();
        Set<LocalDateTime> dateLists = new HashSet<>();
        for (Post post : posts) {
            dateLists.add(post.getCreatedAt());
        }
        return dateLists;
    }

    public List<Tag> findOrCreateTaglist(List<Tag> tags) {
        if (tags.isEmpty()) {
            return null;
        }
        List<Tag> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            Tag tagDb = tagRepository.findByName(tag.getName());
            if (tagDb == null) {
                Tag newTag = new Tag();
                newTag.setName(tag.getName());
                newTag.setCreatedAt(LocalDateTime.now());
                newTag.setCreatedAt(LocalDateTime.now());
                tagList.add(newTag);
            } else {
                tagList.add(tagDb);
            }
        }
        return tagList;
    }

    public List<Post> findByKeyword(String keyword) {
        try {
            return this.postRepository.searchByKeyword(keyword);
        } catch (Exception e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

//    public List<Post> filterPost() {
//        Set<String> authorNameList = this.getAuthorNameList();
//        Set<String> tagNameList = this.getTagNameList();
//        Set<LocalDateTime> dateList = this.getDateList();
//        return this.postRepository.
//                findPostsByAuthorsAndPublishedDateAndTags(authorNameList,dateList,tagNameList);
//    }
}
