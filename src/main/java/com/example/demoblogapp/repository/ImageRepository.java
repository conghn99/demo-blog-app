package com.example.demoblogapp.repository;

import com.example.demoblogapp.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query("select i from Image i where i.user.id = ?1 order by i.created_at DESC")
    List<Image> findByUser_IdOrderByCreated_atDesc(Integer id);

}
