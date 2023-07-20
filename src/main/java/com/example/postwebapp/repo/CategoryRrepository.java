package com.example.postwebapp.repo;

import com.example.postwebapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRrepository  extends JpaRepository<Category,Long> {
}
