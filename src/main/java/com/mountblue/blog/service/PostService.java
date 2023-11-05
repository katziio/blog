package com.mountblue.blog.service;

import com.mountblue.blog.dto.PageDto;
import com.mountblue.blog.dto.PostDto;
import com.mountblue.blog.model.*;
import com.mountblue.blog.repository.PostAndTagsRepository;
import com.mountblue.blog.repository.PostsRepository;
import com.mountblue.blog.repository.TagsRepository;
import com.surendra.BlogApplication.model.*;
import com.mountblue.blog.repository.UserInfoRepository;
import com.mountblue.blog.specification.PostSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final int PAGESIZE = 10;
    private final boolean TRUE = true;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PostAndTagsRepository postAndTagsRepository;
    private PostSpecifications postSpecifications = new PostSpecifications();

    public long getSavedPostId(PostDto postDto) {
        postDto.setAuthor(userInfoRepository.findByEmail(getUserEmail()).get().getUsername());
        String content = postDto.getContent();
        if (content.length() < 100) {
            postDto.setExcerpt(content);
        } else {
            postDto.setExcerpt(content.substring(0, 100));
        }
        if (postDto.getTags() == null) {
            return postsRepository.save(convertDtoToDao(postDto)).getId();
        } else {
            List<Tag> tags = saveTags(postDto.getTags().split("\\s+"));
            Post post = convertDtoToDao(postDto);
            return savePost(post, tags);
        }
    }

    public long savePost(Post post, List<Tag> tags) {
        List<PostAndTag> postAndTags = new ArrayList<>();
        for (Tag tag : tags) {
            PostAndTag postAndTag = new PostAndTag();
            postAndTag.setPost(post);
            postAndTag.setTag(tag);
            postAndTags.add(postAndTag);
            tag.getPostAndTags().add(postAndTag);
        }
        tagsRepository.saveAll(tags);
        post.getPostAndTags().addAll(postAndTags);
        Post savedPost = postsRepository.save(post);
        User user = userInfoRepository.findByEmail(getUserEmail()).get();
        user.getPosts().add(savedPost);
        userInfoRepository.save(user);
        return savedPost.getId();
    }

    public void updatePost(PostDto postDto) {
        Post post = postsRepository.findById(postDto.getPostId()).get();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setPublished(TRUE);
        if (postDto.getContent().length() < 100) {
            post.setExcerpt(postDto.getContent());
        } else {
            post.setExcerpt(postDto.getContent().substring(0, 100));
        }
        if (postDto.getTags() == null) {
            post.getPostAndTags().clear();
            postsRepository.save(post);
            tagsRepository.deleteUnusedTags();
            return;
        }
        List<Tag> tags = saveTags(postDto.getTags().split("\\s+"));
        Set<Tag> oldTags = new HashSet<>();
        for (PostAndTag postAndTag : post.getPostAndTags()) {
            oldTags.add(postAndTag.getTag());
        }
        oldTags.retainAll(tags);
        Iterator<PostAndTag> iterator = post.getPostAndTags().iterator();
        while (iterator.hasNext()) {
            PostAndTag postAndTag = iterator.next();
            if (!oldTags.contains(postAndTag.getTag())) {
                iterator.remove();
            }
        }
        tags.removeAll(oldTags);
        savePost(post, tags);
        tagsRepository.deleteUnusedTags();
    }

    public PageDto fetchPosts(PageDto pageDto) {
        int page = pageDto.getPage();
        String sortBy = pageDto.getSortBy();
        String search = pageDto.getSearch();
        List<String> authors = pageDto.getAuthors();
        List<String> tags = pageDto.getTags();

        Pageable pageable = PageRequest.of(page - 1, PAGESIZE, Sort.by(sortBy));
        Specification<Post> postSpecification = getPostsOnFilters(authors, tags, pageDto.getDate(), search);
        Page<Post> postPage = postsRepository.findAll(postSpecification, pageable);
        int totalPages = postPage.getTotalPages();
        List<Post> posts = postPage.getContent();

        pageDto.setBlogs(posts
                .stream()
                .map(this::convertDaoToDto)
                .collect(Collectors.toList()));
        pageDto.setTotalTags(getTotalTags());
        pageDto.setTotalAuthors(getTotalAuthors());
        pageDto.setTotalPages(totalPages);
        return pageDto;
    }

    public PostDto getPostById(long id) {
        try {
            return convertDaoToDto(Objects.requireNonNull(postsRepository.findById(id).orElse(null)));
        } catch (Exception e) {
            return null;
        }
    }

    public PostDto convertDaoToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setAuthor(post.getAuthor());
        postDto.setContent(post.getContent());
        postDto.setExcerpt(post.getExcerpt());
        postDto.setPublishedAt(post.getPublishedAt());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        postDto.setComments(post.getComments());
        postDto.setCheckAuthor(checkAuthor(post.getId()));
        postDto.setPostAndTag(post.getPostAndTags());
        postDto.setPublished(TRUE);
        String tags = "";
        if (post.getPostAndTags() != null) {
            for (PostAndTag postAndTag : post.getPostAndTags()) {
                tags += postAndTag.getTag().getName() + " ";
            }
        }
        postDto.setTags(tags.trim());
        return postDto;
    }

    public Post convertDtoToDao(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setExcerpt(postDto.getExcerpt());
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        post.setPublished(TRUE);
        post.setPostAndTags(postDto.getPostAndTag());
        post.setComments(postDto.getComments());
        return post;
    }

    public void saveCommentInPost(Comment comment, long id) {
        Post post = postsRepository.findById(id).orElse(null);
        post.getComments().add(comment);
        postsRepository.save(post);
    }

    public List<Tag> saveTags(String[] totalTags) {
        List<Tag> tags = new ArrayList<>();
        for (String singleTag : totalTags) {
            Tag tag = tagsRepository.findByName(singleTag);
            if (tag == null) {
                tag = new Tag();
                tag.setName(singleTag);
            }
            tags.add(tag);
        }
        return tags;
    }

    public List<String> getTotalTags() {
        return (tagsRepository.findAll()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
    }

    public Set<String> getTotalAuthors() {
        return (postsRepository.findAll()
                .stream()
                .map(Post::getAuthor)
                .collect(Collectors.toSet()));
    }

    public Specification<Post> getPostsOnFilters(List<String> authors, List<String> tags, LocalDate date, String search) {
        Specification<Post> specification = Specification.where(null);
        if (search != null) {
            specification = specification.and(postSpecifications.filterPostsOnKeyword(search));
        }
        if (authors != null && !authors.isEmpty()) {
            specification = specification.and(postSpecifications.filterPostsOnAuthors(authors));
        }
        if (date != null) {
            specification = specification.and(postSpecifications.filterPostsOnPublishedDate(date));
        }
        if (tags != null && !tags.isEmpty()) {
            specification = specification.and(postSpecifications.filterPostsOnTags(tags));
        }
        return specification;
    }

    public String getUserEmail() {
        Authentication authentication = getAuth();
        if (authentication == null) return null;
        Object principal = authentication.getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

    public boolean checkAuthor(long id) {
        try {
            Post post = postsRepository.findById(id).orElse(null);
            if (post == null)
                return true;
            return Objects.requireNonNull(userInfoRepository.findByEmail(getUserEmail()).orElse(null)).getPosts().contains(post);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePostById(long id) {
        try {
            postAndTagsRepository.deletePostAndTag(id);
            Post post = postsRepository.findById(id).orElse(null);
            post.getComments().clear();
            postsRepository.save(post);
            postsRepository.deletePost(id);
            tagsRepository.deleteUnusedTags();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean authorize(long postId) {
        Authentication authentication = getAuth();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return true;
            }
            return checkAuthor(postId);
        }
        return false;
    }

    public Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
