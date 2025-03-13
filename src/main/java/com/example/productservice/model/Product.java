package com.example.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Product extends BaseModel implements Serializable {
    private String title;
    private String description;
    private String imageURL;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Category category;

}
