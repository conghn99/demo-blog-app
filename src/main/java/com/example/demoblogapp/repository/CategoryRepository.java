package com.example.demoblogapp.repository;

import com.example.demoblogapp.dto.CategoryDTO;
import com.example.demoblogapp.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Set<Category> findByIdIn(List<Integer> ids);

    @Query("select new com.example.demoblogapp.dto.CategoryDTO(ed) from Category ed")
    List<CategoryDTO> getAllCategory();

    @Query("select new com.example.demoblogapp.dto.CategoryDTO(ed) from Category ed join ed.blogs b group by ed.id order by count(b) desc")
    List<CategoryDTO> getTopFiveCategory();
}
