package com.example.postwebapp.repo;

import com.example.postwebapp.entity.Category;
import com.example.postwebapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByCategory(Category category);
}
