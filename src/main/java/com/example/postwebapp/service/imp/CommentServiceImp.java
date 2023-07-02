package com.example.postwebapp.service.imp;

import com.example.postwebapp.dtos.CommentDto;
import com.example.postwebapp.dtos.CommentResponse;
import com.example.postwebapp.dtos.PostDto;
import com.example.postwebapp.entity.Comment;
import com.example.postwebapp.entity.Post;
import com.example.postwebapp.exception.BlogAPIExeption;
import com.example.postwebapp.exception.ResourceNotFoundException;
import com.example.postwebapp.repo.CommentRepository;
import com.example.postwebapp.repo.PostRepository;
import com.example.postwebapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(comment.getBody());
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
    private boolean commentPostMatch(Post post , Comment comment){
        return comment.getPost().getId() == post.getId();
    }
    @Override
    public CommentDto createComment(Long postId , CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);

    }

    @Override
    public CommentResponse getCommentByPostId(Long id) {
        List<Comment> comments = commentRepository.findByPostId(id);
        Post post = postRepository.getById(id);
        List<CommentDto> commentDtos =  comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentDtos(commentDtos);
        commentResponse.setPostTitle(post.getTitle());
        return commentResponse;
    }

    @Override
    public CommentDto getCommmetById(Long postId , Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if (!commentPostMatch(post,comment)){
            throw new BlogAPIExeption("Content not match" , HttpStatus.BAD_REQUEST);

        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if (!commentPostMatch(post,comment)){
            throw new BlogAPIExeption("Content not match" , HttpStatus.BAD_REQUEST);
        }
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "id" , postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if (!commentPostMatch(post,comment)){
            throw new BlogAPIExeption("Content not match" , HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(comment);
    }

}
