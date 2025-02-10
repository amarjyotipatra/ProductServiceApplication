package com.example.productservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @GetMapping("/products")
    public void getAllProducts() {

    }

    @GetMapping("/products/{id}")
    public void getProductById(@PathVariable("id") int id) {

    }

    @PostMapping("/products")
    public void createProduct() {}

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable("id") int id) {}

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable("id") int id) {}
}
