package com.example.postwebapp.service.imp;

import com.example.postwebapp.dtos.CategoryDto;
import com.example.postwebapp.dtos.CategoryResponse;
import com.example.postwebapp.entity.Category;
import com.example.postwebapp.exception.ResourceNotFoundException;
import com.example.postwebapp.repo.CategoryRrepository;
import com.example.postwebapp.service.CategoryService;
import com.example.postwebapp.utilty.ApiFeatures;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    private CategoryRrepository categoryRrepository;
    private ModelMapper modelMapper;

    public CategoryServiceImp(CategoryRrepository categoryRrepository, ModelMapper modelMapper) {
        this.categoryRrepository = categoryRrepository;
        this.modelMapper = modelMapper;
    }
    private Category mapToEntity(CategoryDto categoryDto){
        Category category= modelMapper.map(categoryDto,Category.class);
        return category;
    }
    private CategoryDto mapToDto(Category category){
        CategoryDto categoryDto= modelMapper.map(category,CategoryDto.class);
        return categoryDto;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category= mapToEntity(categoryDto);
        Category savedCategory =categoryRrepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRrepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category" , "id" , id));
        return mapToDto(category);
    }

    @Override
    public CategoryResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Pageable pageable = ApiFeatures.filter(pageNo,pageSize,sortBy,sortDir);
        Page<Category> categoryPage = categoryRrepository.findAll(pageable);
        List<Category> categoryList = categoryPage.getContent();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtoList);
        categoryResponse.setPageNo(pageNo);
        categoryResponse.setTotalPage(categoryPage.getTotalPages());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryResponse.getTotalElements());
        categoryResponse.setLast(categoryResponse.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRrepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category" , "id" , id));
        category.setDescription(categoryDto.getDescription());
        category.setName(category.getName());
        Category saveedCategory = categoryRrepository.save(category);

        return mapToDto(saveedCategory);
    }
}
