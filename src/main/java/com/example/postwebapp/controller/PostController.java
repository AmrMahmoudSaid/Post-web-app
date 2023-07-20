package com.example.postwebapp.controller;

import com.example.postwebapp.dtos.PostDto;
import com.example.postwebapp.dtos.PostResponse;
import com.example.postwebapp.service.PostService;
import com.example.postwebapp.utilty.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("/create-post")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping("/list-posts")
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy" ,defaultValue = AppConstants.DEFAULT_SORT_BY , required = false) String sortBy,
            @RequestParam(value = "sortDir" ,defaultValue = AppConstants.DEFAULT_SORT_DIRECTION , required = false) String sortDir
    ){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){
        return  ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<List<PostDto>> getAllPostByCategoryId(@PathVariable("category_id") Long id){
        return ResponseEntity.ok(postService.getAllPostByCategoryId(id));
    }
    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/update-post/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto , @PathVariable(name = "id") Long id){
        return  ResponseEntity.ok(postService.updatePost(postDto,id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post entity deleted successfully");
    }

}
