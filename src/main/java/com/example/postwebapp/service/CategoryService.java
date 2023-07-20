package com.example.postwebapp.service;

import com.example.postwebapp.dtos.CategoryDto;
import com.example.postwebapp.dtos.CategoryResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);

    CategoryResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);
}
