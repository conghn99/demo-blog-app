package com.example.demoblogapp.repository;

import com.example.demoblogapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Set<Category> findByIdIn(List<Integer> ids);
}
