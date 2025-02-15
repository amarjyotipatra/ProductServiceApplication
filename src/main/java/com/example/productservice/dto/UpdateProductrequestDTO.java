package com.example.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductrequestDTO {
    private Integer id;
    private String title;
    private String imageURL;
    private String description;
    private CategoryRequestDTO category;
}
