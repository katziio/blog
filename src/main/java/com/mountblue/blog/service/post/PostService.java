package com.mountblue.blog.service.post;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.model.PostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    public PostDto addPost(Post post);
    public PostDto updatePost(Post post);
    public PostDto deletePost(Long postId);
    public PostDto findPostById(Long postId);
    public Pageable pageable(int page, int size, String sortField, String orderBy);
    public List<Post> filterByTagsNames(List<String> tags);
}
