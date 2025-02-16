package com.example.productservice.repository;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    // Select * from products where id = id;
    Optional<Product> findById(int id);

    Product save(Product p);

    Optional<Product> findByCategory(Category c);

    // Select * from products where id = id and category = c;
    Optional<Product> findByIdAndCategory(int id, Category c);

    Optional<List<Product>> findAllByCategory(Category c);

    void deleteById(int id);

    void deleteAllByCategory(Category c);
}
