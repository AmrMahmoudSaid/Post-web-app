package com.example.postwebapp.service;

import com.example.postwebapp.dtos.CommentDto;
import com.example.postwebapp.dtos.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId , CommentDto commentDto);
    CommentResponse getCommentByPostId(Long id);
    CommentDto getCommmetById(Long postId , Long commentId);
    CommentDto updateCommentById(Long postId , Long commentId ,CommentDto commentDto );
    void deleteComment(Long postId , Long commentId);
}
