package com.example.demoblogapp.dto;

import com.example.demoblogapp.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Integer id;
    private String name;
    private int used;

    public CategoryDTO(Category entity) {
        this.setId(entity.getId());
        this.setName(entity.getName());
        this.setUsed(entity.getBlogs().size());
    }
}
