package com.example.productservice.controller;

import com.example.productservice.dto.CreateProductRequestDTO;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private  ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products=service.getAllProducts();
        if(products.size()==0 || products==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        //validations
//        if(id == null) throw new IllegalArgumentException("id is null");
        return service.getProductById(id);
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDTO request) {
        if(request.getTitle()==null)
            throw new IllegalArgumentException("Title cannot be null");
        if(request.getDescription()==null)
            throw new IllegalArgumentException("Description cannot be null");
        if(request.getCategory()==null)
            throw new IllegalArgumentException("Category cannot be null");
        if(request.getImageURL()==null)
            throw new IllegalArgumentException("ImageURL cannot be null");

        return service.createProduct(request.getTitle(), request.getImageURL(), request.getCategory().getTitle(),request.getDescription());
    }

    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable("id") int id) {}

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable("id") int id) {}
}
