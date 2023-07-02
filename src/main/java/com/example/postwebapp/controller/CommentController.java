package com.example.postwebapp.controller;

import com.example.postwebapp.dtos.CommentDto;
import com.example.postwebapp.dtos.CommentResponse;
import com.example.postwebapp.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId ,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> getAllCommentByPostId(@PathVariable(name = "postId") Long postId){
        return new ResponseEntity<>(commentService.getCommentByPostId(postId),HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> getCommentByid(
            @PathVariable(name = "postId") Long postId ,
            @PathVariable(name = "commentId") Long commentId){
        return new ResponseEntity<>(commentService.getCommmetById(postId,commentId),HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> updatePostById(
            @PathVariable(name = "postId") Long postId ,
            @PathVariable(name = "commentId") Long commentId , @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto),HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<String> deletePostById(
            @PathVariable(name = "postId") Long postId ,
            @PathVariable(name = "commentId") Long commentId
    ){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Post entity deleted successfully");
    }

}
