package com.example.productservice.repository;

import com.example.productservice.model.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findByTitleAndIsDeletedFalse(String title);
    Optional<Category> findByIdAndIsDeletedFalse(int id);
    Category save(Category c);
}
