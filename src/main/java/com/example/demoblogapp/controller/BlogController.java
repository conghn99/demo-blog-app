package com.example.demoblogapp.controller;

import com.example.demoblogapp.request.UpsertBlogRequest;
import com.example.demoblogapp.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogController {
    @Autowired
    private BlogService blogService;

    // Xem danh sach blog
    @GetMapping("/api/admin/blogs")
    public ResponseEntity<?> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlog()); //200
    }

    @GetMapping("/api/public/blogs")
    public ResponseEntity<?> getAllBlogsByPublicStatus() {
        return ResponseEntity.ok(blogService.getAllBLogByPublicStatus());
    }

    @GetMapping("/api/public/blogs/{id}")
    public ResponseEntity<?> getBlogByIdByPublicStatus(@PathVariable Integer id) {
        return ResponseEntity.ok(blogService.getBLogByStatusPublicStatus(id));
    }

    @GetMapping("/api/public/blogs/search")
    public ResponseEntity<?> getBlogByKeyword(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(blogService.getAllBlogByKeyword(keyword));
    }

    @GetMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<?> getBlogsByPublicWithCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(blogService.getBlogByPublicWithCategory(categoryId));
    }

    // Tao blog
    @PostMapping("/api/admin/blogs")
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest request) {
        return new ResponseEntity<>(blogService.createBlog(request), HttpStatus.CREATED); //201
    }

    // Xem chi tiet blog
    @GetMapping("/api/admin/blogs/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable Integer id) {
        return ResponseEntity.ok(blogService.getBlogById(id)); //200
    }

    // Cap nhat blog
    @PutMapping("/api/admin/blogs/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody UpsertBlogRequest request) {
        return ResponseEntity.ok(blogService.updateBlog(id, request)); //200
    }

    // Xoa blog
    @DeleteMapping("/api/admin/blogs/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build(); //204
    }
}
