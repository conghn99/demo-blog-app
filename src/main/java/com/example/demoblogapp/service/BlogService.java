package com.example.demoblogapp.service;

import com.example.demoblogapp.entity.Blog;
import com.example.demoblogapp.entity.Category;
import com.example.demoblogapp.entity.User;
import com.example.demoblogapp.exception.NotFoundException;
import com.example.demoblogapp.repository.BlogRepository;
import com.example.demoblogapp.repository.CategoryRepository;
import com.example.demoblogapp.repository.UserRepository;
import com.example.demoblogapp.request.UpsertBlogRequest;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final Slugify slugify;

    public List<Blog> getAllBlog() {
        return blogRepository.findAll();
    }

    @Transactional
    public Blog createBlog(UpsertBlogRequest request) {
        // validate thong tin

        // tim kiem category
        Set<Category> categories = categoryRepository.findByIdIn(request.getCategoryId());

        //  user chinh la user dang dang nhap
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotFoundException("Not found user with email = " + email);
        });

        // tao blog
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .slug(slugify.slugify(request.getTitle()))
                .content(request.getContent())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .status(request.isStatus())
                .categories(categories)
                .user(user)
                .build();
        return blogRepository.save(blog);
    }

    public Blog getBlogById(Integer id) {
        return blogRepository.findById(id).orElseThrow(() -> {
           throw new NotFoundException("Not found blog with id = " + id);
        });
    }

    @Transactional
    public Blog updateBlog(Integer id, UpsertBlogRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });

        Set<Category> categories = categoryRepository.findByIdIn(request.getCategoryId());

        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(request.getTitle()));
        blog.setContent(request.getContent());
        blog.setDescription(request.getDescription());
        blog.setStatus(request.isStatus());
        blog.setCategories(categories);
        blog.setThumbnail(request.getThumbnail());

        return blogRepository.save(blog);
    }

    @Transactional
    public void deleteBlog(Integer id) {
        // TODO : Khi xoa blog thi can than vi lien quan den comment va category (co the su dung lifecycle (preRemove))
        // Xoa blog -> xoa comment
        // Xoa blog -> xoa blog-category trong bang trung gian, ko xoa category
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });
        blogRepository.delete(blog);
    }

    public List<Blog> getAllBLogByPublicStatus() {
        return blogRepository.findBlogsByStatusOrderByPublishedAtDesc(true);
    }

    public Blog getBLogByStatusPublicStatus(Integer id) {
        return blogRepository.findBlogByIdAndStatus(id, true).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });
    }

    public List<Blog> getBlogByPublicWithCategory(Integer categoryId) {
        return blogRepository.findBlogsByCategoryId(categoryId, true);
    }

    public List<Blog> getAllBlogByKeyword(String keyword) {
        return blogRepository.findByTitleContainsIgnoreCaseAndStatusOrderByPublishedAtDesc(keyword, true);
    }
}
