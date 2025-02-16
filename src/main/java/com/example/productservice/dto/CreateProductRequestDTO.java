package com.example.productservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequestDTO {
    private String title;
    private String imageURL;
    private String description;
    private CategoryRequestDTO category;
}
