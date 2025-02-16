package com.example.productservice.dto;

import com.example.productservice.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private int id;
    private String title;
    private String category;
    private String description;
    private String image;
}
