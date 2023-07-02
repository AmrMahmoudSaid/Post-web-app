package com.example.postwebapp.dtos;

import com.example.postwebapp.entity.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Name should not be null or empty ")
    private String name;
    @NotEmpty(message = "Email should not be null or empty ")
    private String email;
    private String body;

}
