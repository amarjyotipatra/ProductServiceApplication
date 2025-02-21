package com.example.productservice.repository;

import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //HQL queries

    @Query("select p.title, p.description from Product p where p.title=:title")
    ProductProjection getProductNameByTitle(@Param("title") String title);

    @Query("select p from Product p where p.title=:title and p.id=:id")
    Product getProductByTitleAndProductId(@Param("title") String title, @Param("id") int id);

}
