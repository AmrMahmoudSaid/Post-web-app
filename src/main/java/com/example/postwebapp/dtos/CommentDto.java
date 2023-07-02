package com.example.postwebapp.dtos;

import com.example.postwebapp.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;

}
