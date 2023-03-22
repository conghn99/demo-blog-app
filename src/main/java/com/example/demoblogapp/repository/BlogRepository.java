package com.example.demoblogapp.repository;

import com.example.demoblogapp.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findBlogsByStatusOrderByPublishedAtDesc(boolean status);

    Optional<Blog> findBlogByIdAndStatus(Integer id, boolean status);

    List<Blog> findByTitleContainsIgnoreCaseAndStatusOrderByPublishedAtDesc(String title, Boolean status);

    @Query("SELECT b FROM Blog b JOIN b.categories c WHERE c.id = ?1 and b.status = ?2 group by b.id order by b.publishedAt desc")
    List<Blog> findBlogsByCategoryId(Integer id, boolean status);
}
