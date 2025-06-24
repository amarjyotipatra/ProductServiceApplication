package com.example.productservice.service;

import com.example.productservice.dto.UpdateProductrequestDTO;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.repository.CategoryRepo;
import com.example.productservice.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SelfProductServiceTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private SelfProductService selfProductService;

    private Product sampleProduct;
    private Category sampleCategory;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        // Create sample category
        sampleCategory = new Category();
        sampleCategory.setId(1);
        sampleCategory.setTitle("Electronics");

        // Create sample product
        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setTitle("Test Product");
        sampleProduct.setDescription("Test Description");
        sampleProduct.setImageURL("http://test.com/image.jpg");
        sampleProduct.setCategory(sampleCategory);
        sampleProduct.setCreatedAt(new Date());
        sampleProduct.setUpdatedAt(new Date());
        sampleProduct.setDeleted(false);

        // Create product list
        productList = new ArrayList<>();
        productList.add(sampleProduct);

        Product product2 = new Product();
        product2.setId(2);
        product2.setTitle("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setImageURL("http://test.com/image2.jpg");
        product2.setCategory(sampleCategory);
        product2.setDeleted(false);
        productList.add(product2);
    }

    @Test
    void getProductById_ReturnsProduct_WhenValidId() throws ProductNotFoundException {
        // Arrange
        when(productRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(sampleProduct));

        // Act
        Product result = selfProductService.getProductById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getTitle());
        verify(productRepo).findByIdAndIsDeletedFalse(1);
    }

    @Test
    void getProductById_ThrowsException_WhenProductNotFound() {
        // Arrange
        when(productRepo.findByIdAndIsDeletedFalse(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> selfProductService.getProductById(100));
        verify(productRepo).findByIdAndIsDeletedFalse(100);
    }

    @Test
    void getAllProducts_ReturnsProductList() {
        // Arrange
        when(productRepo.findAllByIsDeletedFalse()).thenReturn(productList);

        // Act
        List<Product> result = selfProductService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Product", result.get(0).getTitle());
        assertEquals("Test Product 2", result.get(1).getTitle());
        verify(productRepo).findAllByIsDeletedFalse();
    }

    @Test
    void getPaginatedProducts_ReturnsPagedProducts() {
        // Arrange
        Page<Product> pagedResponse = new PageImpl<>(productList);
        when(productRepo.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        // Act
        Page<Product> result = selfProductService.getPaginatedProducts(1, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Test Product", result.getContent().get(0).getTitle());
        verify(productRepo).findAll(any(Pageable.class));
    }

    @Test
    void createProduct_ReturnsNewProduct_WhenCategoryExists() {
        // Arrange
        when(categoryRepo.findByTitleAndIsDeletedFalse("Electronics")).thenReturn(Optional.of(sampleCategory));
        when(productRepo.save(any(Product.class))).thenReturn(sampleProduct);

        // Act
        Product result = selfProductService.createProduct("Test Product", "http://test.com/image.jpg", "Electronics", "Test Description");

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals("Electronics", result.getCategory().getTitle());
        verify(categoryRepo).findByTitleAndIsDeletedFalse("Electronics");
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void createProduct_CreatesNewCategory_WhenCategoryDoesNotExist() {
        // Arrange
        when(categoryRepo.findByTitleAndIsDeletedFalse("NewCategory")).thenReturn(Optional.empty());

        Category newCategory = new Category();
        newCategory.setId(2);
        newCategory.setTitle("NewCategory");

        when(categoryRepo.save(any(Category.class))).thenReturn(newCategory);
        when(productRepo.save(any(Product.class))).thenReturn(sampleProduct);

        // Act
        Product result = selfProductService.createProduct("Test Product", "http://test.com/image.jpg", "NewCategory", "Test Description");

        // Assert
        assertNotNull(result);
        verify(categoryRepo).findByTitleAndIsDeletedFalse("NewCategory");
        verify(categoryRepo).save(any(Category.class));
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void createProduct_ThrowsException_WhenInputInvalid() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            selfProductService.createProduct(null, "http://test.com/image.jpg", "Electronics", "Test Description"));

        assertThrows(IllegalArgumentException.class, () ->
            selfProductService.createProduct("Test Product", null, "Electronics", "Test Description"));

        assertThrows(IllegalArgumentException.class, () ->
            selfProductService.createProduct("Test Product", "http://test.com/image.jpg", null, "Test Description"));

        assertThrows(IllegalArgumentException.class, () ->
            selfProductService.createProduct("Test Product", "http://test.com/image.jpg", "Electronics", null));
    }

    @Test
    void updateProduct_ReturnsUpdatedProduct_WhenProductExists() throws ProductNotFoundException {
        // Arrange
        UpdateProductrequestDTO updateRequest = new UpdateProductrequestDTO();
        updateRequest.setId(1);
        updateRequest.setTitle("Updated Product");
        updateRequest.setDescription("Updated Description");
        updateRequest.setImageURL("http://test.com/updated.jpg");

        Category category = new Category();
        category.setTitle("Electronics");
        updateRequest.setCategory(category);

        when(productRepo.findByIdAndIsDeletedFalse(1)).thenReturn(Optional.of(sampleProduct));
        when(categoryRepo.findByTitleAndIsDeletedFalse("Electronics")).thenReturn(Optional.of(sampleCategory));

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setTitle("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setImageURL("http://test.com/updated.jpg");
        updatedProduct.setCategory(sampleCategory);

        when(productRepo.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        Product result = selfProductService.updateProduct(updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Product", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        verify(productRepo).findByIdAndIsDeletedFalse(1);
        verify(categoryRepo).findByTitleAndIsDeletedFalse("Electronics");
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void updateProduct_ThrowsException_WhenProductNotFound() {
        // Arrange
        UpdateProductrequestDTO updateRequest = new UpdateProductrequestDTO();
        updateRequest.setId(100);
        updateRequest.setTitle("Updated Product");

        when(productRepo.findByIdAndIsDeletedFalse(100)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> selfProductService.updateProduct(updateRequest));
        verify(productRepo).findByIdAndIsDeletedFalse(100);
    }

    @Test
    void deleteProductById_ReturnsDeletedProduct_WhenProductExists() throws ProductNotFoundException {
        // Arrange
        when(productRepo.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productRepo.save(any(Product.class))).thenReturn(sampleProduct);

        // Act
        Product result = selfProductService.deleteProductById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productRepo).findById(1);
        verify(productRepo).save(any(Product.class));
    }

    @Test
    void deleteProductById_ThrowsException_WhenProductNotFound() {
        // Arrange
        when(productRepo.findById(100)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> selfProductService.deleteProductById(100));
        verify(productRepo).findById(100);
    }
}