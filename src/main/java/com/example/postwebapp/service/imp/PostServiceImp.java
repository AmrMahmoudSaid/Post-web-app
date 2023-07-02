package com.example.postwebapp.service.imp;

import com.example.postwebapp.dtos.PostDto;
import com.example.postwebapp.dtos.PostResponse;
import com.example.postwebapp.entity.Post;
import com.example.postwebapp.exception.ResourceNotFoundException;
import com.example.postwebapp.repo.PostRepository;
import com.example.postwebapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImp(PostRepository postRepository,ModelMapper modelMapper) {
        this.modelMapper=modelMapper;
        this.postRepository = postRepository;
    }
    private PostDto mapToDTO(Post p ){
        PostDto postResponse  =modelMapper.map(p,PostDto.class);
//        postResponse.setContent(p.getContent());
//        postResponse.setDescription(p.getDescription());
//        postResponse.setTitle(p.getTitle());
//        postResponse.setId(p.getId());
        return postResponse;
    }
    private Post mapToEntity(PostDto p){
        Post post = modelMapper.map(p,Post.class);
//        post.setContent(p.getContent());
//        post.setDescription(p.getDescription());
//        post.setTitle(p.getTitle());
        return post;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        PostDto postResponse  =mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo , int pageSize ,String sortBy,String sortDir) {
//        Pageable pageable;
//        if (sortDir.equals("dec")){
//             pageable =  PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
//        }else {
//             pageable =  PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
//        }
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "id" , id));
        PostDto responsePost = mapToDTO(post);
        return responsePost;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "id" , id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post" , "id" , id));
        postRepository.delete(post);
    }

}
