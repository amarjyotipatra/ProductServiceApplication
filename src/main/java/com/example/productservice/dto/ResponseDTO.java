package com.example.productservice.dto;

import com.example.productservice.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private Integer id;
    private String title;
    private String category;
    private String description;
    private String image;
}
