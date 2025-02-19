package com.example.productservice.service;

import com.example.productservice.dto.UpdateProductrequestDTO;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(int id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Product createProduct(String title, String imageURL, String category, String description);

    Product updateProduct(UpdateProductrequestDTO request) throws ProductNotFoundException;

    Product deleteProductById(int id) throws ProductNotFoundException;

}
