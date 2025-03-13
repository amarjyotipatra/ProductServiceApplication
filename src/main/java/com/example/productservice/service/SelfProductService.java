package com.example.productservice.service;

import com.example.productservice.dto.UpdateProductrequestDTO;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.CategoryRepo;
import com.example.productservice.repository.ProductRepo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(int id) throws ProductNotFoundException {
        Optional<Product> response= productRepo.findByIdAndIsDeletedFalse(id);
        if(response.isPresent()){
            return response.get();
        }else{
            throw new ProductNotFoundException("Invalid Id :"+ id);
        }
    }

    @Override
    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        return productRepo.findAllByIsDeletedFalse();
    }

    @Override
    public Page<Product> getPaginatedProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        //To make pageNo. 1 based indexing , we need to subtract 1 from pageNo. which is passed from Request Body of controller
        Pageable sortedPageable = PageRequest.of(pageNo - 1 , pageSize, Sort.Direction.ASC, "id");
        Page<Product> productPage = productRepo.findAll(sortedPageable);
        return productPage;
    }


    @Override
    @CachePut(value = "products", key = "#result.id")
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
        Optional<Category> existingCategory= categoryRepo.findByTitleAndIsDeletedFalse(category);
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

    @Override
    @CachePut(value = "products", key = "#result.id")
    public Product updateProduct(UpdateProductrequestDTO request) throws ProductNotFoundException {
        Optional<Product> existingProductOptional= productRepo.findByIdAndIsDeletedFalse(request.getId());

        if(existingProductOptional.isPresent()){
            Product existingProduct = existingProductOptional.get();
            existingProduct.setTitle(request.getTitle());
            existingProduct.setImageURL(request.getImageURL());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setUpdatedAt(new Date());
            if(request.getCategory() != null && request.getCategory().getTitle() != null) {
                Optional<Category> exitingCategory = categoryRepo.findByTitleAndIsDeletedFalse(request.getCategory().getTitle());
                Category categoryObj=exitingCategory.orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setTitle(request.getCategory().getTitle());
                    return categoryRepo.save(newCategory);
                });

                existingProduct.setCategory(categoryObj);
            }
            return productRepo.save(existingProduct);
        }else{
            throw new ProductNotFoundException("Invalid Id :"+ request.getId());
        }

    }

    @Override
    public Product deleteProductById(int id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepo.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(true);
            product.setUpdatedAt(new Date());
            productRepo.save(product);
            return product;
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }
}
