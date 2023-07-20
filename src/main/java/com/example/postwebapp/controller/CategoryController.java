package com.example.postwebapp.controller;

import com.example.postwebapp.dtos.CategoryDto;
import com.example.postwebapp.dtos.CategoryResponse;
import com.example.postwebapp.dtos.PostResponse;
import com.example.postwebapp.entity.Category;
import com.example.postwebapp.service.CategoryService;
import com.example.postwebapp.utilty.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "id") Long id){
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return  ResponseEntity.ok(categoryDto);

    }
    @GetMapping("/all-category")
    public CategoryResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy" ,defaultValue = AppConstants.DEFAULT_SORT_BY , required = false) String sortBy,
            @RequestParam(value = "sortDir" ,defaultValue = AppConstants.DEFAULT_SORT_DIRECTION , required = false) String sortDir
    ){
        return categoryService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(name = "id") Long id , @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(id , categoryDto));
    }




}
