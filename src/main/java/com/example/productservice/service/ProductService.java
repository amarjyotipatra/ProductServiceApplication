package com.example.productservice.service;

import com.example.productservice.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(int id);

    List<Product> getAllProducts();

    Product createProduct(String title, String imageURL, String category, String description);
}
