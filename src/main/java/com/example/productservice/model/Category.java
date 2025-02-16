package com.example.productservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Category extends BaseModel {
    private String title;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
