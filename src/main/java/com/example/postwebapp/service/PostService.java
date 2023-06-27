package com.example.postwebapp.service;

import com.example.postwebapp.dtos.PostDto;
import com.example.postwebapp.dtos.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo , int pageSize,String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto , Long id);
    void deletePostById(Long id);
}
