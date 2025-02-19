package com.example.productservice.repository;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    // Select * from products where id = id;
    Optional<Product> findByIdAndIsDeletedFalse(int id);

    //find all records where isDeleted attribute is false
    List<Product> findAllByIsDeletedFalse();

    Optional<List<Product>> findAllByCategoryAndIsDeletedFalse(Category c);

    Optional<Product> findByCategory(Category c);

    // Select * from products where id = id and category = c;
    Optional<Product> findByIdAndCategory(int id, Category c);

    Optional<List<Product>> findAllByCategory(Category c);

    Optional<Product> deleteById(int id);

    void deleteAllByCategory(Category c);

    Product save(Product p);

}
