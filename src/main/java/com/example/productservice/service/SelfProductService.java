package com.example.productservice.service;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.CategoryRepo;
import com.example.productservice.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public SelfProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> response= productRepo.findById(id);
        if(response.isPresent()){
            return response.get();
        }
        return response.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product createProduct(String title, String imageURL, String category, String description) {
        //validation
        validateInputRequest(title,imageURL,category,description);
        //create a product
        Product product = new Product();
        product.setTitle(title);
        product.setImageURL(imageURL);
        product.setDescription(description);
        product.setCreatedAt(new Date());
        product.setUpdatedAt(new Date());
        //checking if category is present or not in database
        Optional<Category> existingCategory= categoryRepo.findByTitle(category);
        Category categoryObj;

        if(existingCategory.isPresent()){
            // Use the existing category if present
            categoryObj = existingCategory.get();
        } else {
            // Otherwise, create a new category
            categoryObj = new Category();
            categoryObj.setTitle(category);
            // Optionally, save the new category if needed
            categoryObj = categoryRepo.save(categoryObj);
        }
        product.setCategory(categoryObj);
        //save the product object to database
        Product response=productRepo.save(product);
        return response;
    }

    private void validateInputRequest(String title, String imageURL, String category, String description) {
        if(title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if(imageURL == null || imageURL.trim().isEmpty()) {
            throw new IllegalArgumentException("Image URL cannot be empty");
        }
        if(category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
    }
}
