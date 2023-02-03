package com.example.demoblogapp.controller;

import com.example.demoblogapp.request.UpsertCategoryRequest;
import com.example.demoblogapp.request.UpsertUserRequest;
import com.example.demoblogapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getCategoryList() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @PostMapping("")
    public ResponseEntity<?> addCategory(@RequestBody UpsertCategoryRequest request) {
        return new ResponseEntity<>(categoryService.addCategory(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody UpsertUserRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMapping(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
