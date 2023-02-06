package com.example.demoblogapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private List<Blog> blogs;

    @PreRemove
    private void removeCategoryFromBlog() {
        for (Blog b : blogs) {
            b.getCategories().remove(this);
        }
    }
}