package com.example.demoblogapp.service;

import com.example.demoblogapp.entity.Category;
import com.example.demoblogapp.exception.NotFoundException;
import com.example.demoblogapp.repository.CategoryRepository;
import com.example.demoblogapp.request.UpsertCategoryRequest;
import com.example.demoblogapp.request.UpsertUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category addCategory(UpsertCategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .build();
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found category with id = " + id);
        });
    }

    @Transactional
    public Category updateCategory(Integer id, UpsertUserRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found category with id = " + id);
        });

        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found category with id = " + id);
        });

        categoryRepository.delete(category);
    }
}
