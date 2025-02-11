package com.example.productservice.controller;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private  ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public void getAllProducts() {

    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        //validations
//        if(id == null) throw new IllegalArgumentException("id is null");
        return service.getProductById(id);
    }

    @PostMapping("/products")
    public void createProduct() {}

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable("id") int id) {}

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable("id") int id) {}
}
