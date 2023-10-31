package com.mountblue.blog.service;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import com.mountblue.blog.model.PostDto;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    public PostDto addPost(Post post)
    {
        try {
            post.setCreated_at(LocalDateTime.now());
            post.setUpdated_at(LocalDateTime.now());
            this.postRepository.save(post);
            return new PostDto(post);
        }
        catch (Exception e)
        {
            throw new RuntimeException("post not added");
        }
    }

    public PostDto updatePost(Post post)
    {
        try {
            post.setUpdated_at(LocalDateTime.now());
            this.postRepository.save(post);
            return new PostDto(post);
        }
        catch (Exception e)
        {
            throw new RuntimeException("post not updated");
        }
    }

    @Transactional
    public PostDto deletePost(Long postId)
    {
        Optional<Post> post = this.postRepository.findById(postId);
        if(post.isPresent())
        {
            this.postRepository.delete(post.get());
            return new PostDto(post.get());
        }
        else {
            throw new RuntimeException("post not deleted");
        }
    }

    public List<PostDto> getPostLists(int page,int size,String sortBy,String orderBy)
    {
        Pageable pageable = this.pageable(page,size,sortBy,orderBy);
        return this.postRepository.findAllPosts(pageable);
    }

    public PostDto findPostById(Long postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        if(post.isPresent())
        {
            return new PostDto(post.get());
        }else {
            throw new RuntimeException("Post not found for id"+postId);
        }
    }

    public Pageable pageable(int page, int size, String sortField,String orderBy)
    {
        Map<String, String> sortFieldMapping = new HashMap<>();
        sortFieldMapping.put("title", "title");
        sortFieldMapping.put("author", "author");
        sortFieldMapping.put("published", "published_at");
        sortFieldMapping.put("created", "created_at");
        sortFieldMapping.put("updated", "updated_at");
        String sortedField = sortFieldMapping.get(sortField);
        if (sortedField == null) {
            sortedField = "author";
        }
        Sort sort = null;
        if(orderBy.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(sortedField));
        }else {
            sort = Sort.by(Sort.Order.asc(sortedField));
        }
        return PageRequest.of(page,size, sort);
    }

    public Set<String> getTagNameList(){
        List<Tag> tags = this.tagRepository.findAll();
        Set<String> tagList = new HashSet<>();
        for (Tag tag:tags)
        {
            tagList.add(tag.getName());
        }
        return tagList;
    }

    public Set<String> getAuthorNameList(){
        List<Post> posts = this.postRepository.findAll();
        Set<String> authorNameList = new HashSet<>();
        for (Post post : posts)
        {
            authorNameList.add(post.getAuthor());
        }
        return authorNameList;
    }

    public Set<LocalDateTime> getDateList(){
        List<Post> posts = this.postRepository.findAll();
        Set<LocalDateTime> dateLists = new HashSet<>();
        for (Post post : posts)
        {
            dateLists.add(post.getCreated_at());
        }
        return dateLists;
    }

//    public List<Post> filterPost() {
//        Set<String> authorNameList = this.getAuthorNameList();
//        Set<String> tagNameList = this.getTagNameList();
//        Set<LocalDateTime> dateList = this.getDateList();
//        return this.postRepository.
//                findPostsByAuthorsAndPublishedDateAndTags(authorNameList,dateList,tagNameList);
//    }
}
