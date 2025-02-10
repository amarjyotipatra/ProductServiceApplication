package com.example.productservice.dto;

import com.example.productservice.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private int id;
    private String title;
    private String description;
    private String imageURL;
    private Category category;
}
