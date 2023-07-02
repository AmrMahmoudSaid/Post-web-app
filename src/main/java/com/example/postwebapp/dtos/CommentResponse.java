package com.example.postwebapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String PostTitle;
    private List<CommentDto> commentDtos;


}
