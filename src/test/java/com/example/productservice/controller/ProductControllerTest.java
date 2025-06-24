package com.example.productservice.controller;

import com.example.productservice.dto.CategoryRequestDTO;
import com.example.productservice.dto.CreateProductRequestDTO;
import com.example.productservice.dto.UpdateProductrequestDTO;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product sampleProduct;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample category
        Category category = new Category();
        category.setId(1);
        category.setTitle("Electronics");

        // Create sample product
        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setTitle("Test Product");
        sampleProduct.setDescription("Test Description");
        sampleProduct.setImageURL("http://test.com/image.jpg");
        sampleProduct.setCategory(category);
        sampleProduct.setCreatedAt(new Date());
        sampleProduct.setUpdatedAt(new Date());

        // Create product list for testing
        productList = new ArrayList<>();
        productList.add(sampleProduct);

        Product product2 = new Product();
        product2.setId(2);
        product2.setTitle("Test Product 2");
        product2.setDescription("Test Description 2");
        product2.setImageURL("http://test.com/image2.jpg");
        product2.setCategory(category);
        productList.add(product2);
    }

    @Test
    void getAllProducts_ReturnsProductsList() throws ProductNotFoundException {
        // Arrange
        when(productService.getAllProducts()).thenReturn(productList);

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Product", response.getBody().get(0).getTitle());
        assertEquals("Test Product 2", response.getBody().get(1).getTitle());
    }

    @Test
    void getAllProducts_ThrowsException_WhenNoProductsFound() {
        // Arrange
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productController.getAllProducts());
    }

    @Test
    void getProductById_ReturnsProduct_WhenValidId() throws ProductNotFoundException {
        // Arrange
        when(productService.getProductById(1)).thenReturn(sampleProduct);

        // Act
        ResponseEntity<Product> response = productController.getProductById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getTitle());
    }

    @Test
    void getProductById_ThrowsException_WhenInvalidId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productController.getProductById(-1));
    }

    @Test
    void getProductById_ThrowsException_WhenProductNotFound() throws ProductNotFoundException {
        // Arrange
        when(productService.getProductById(anyInt())).thenReturn(null);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(100));
    }

    @Test
    void getProductsByPage_ReturnsPagedProducts() {
        // Arrange
        Page<Product> pagedProducts = new PageImpl<>(productList);
        when(productService.getPaginatedProducts(1, 10)).thenReturn(pagedProducts);

        // Act
        ResponseEntity<Page<Product>> response = productController.getProductsByPage(1, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    void createProduct_ReturnsCreatedProduct() {
        // Arrange
        CreateProductRequestDTO requestDTO = new CreateProductRequestDTO();
        requestDTO.setTitle("New Product");
        requestDTO.setDescription("New Description");
        requestDTO.setImageURL("http://test.com/new.jpg");

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setTitle("Electronics");
        requestDTO.setCategory(categoryDTO);

        when(productService.createProduct(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(sampleProduct);

        // Act
        ResponseEntity<Product> response = productController.createProduct(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void createProduct_ThrowsException_WhenTitleIsNull() {
        // Arrange
        CreateProductRequestDTO requestDTO = new CreateProductRequestDTO();
        requestDTO.setDescription("New Description");
        requestDTO.setImageURL("http://test.com/new.jpg");

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setTitle("Electronics");
        requestDTO.setCategory(categoryDTO);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productController.createProduct(requestDTO));
    }

    @Test
    void updateProduct_ReturnsUpdatedProduct() throws ProductNotFoundException {
        // Arrange
        UpdateProductrequestDTO requestDTO = new UpdateProductrequestDTO();
        requestDTO.setId(1);
        requestDTO.setTitle("Updated Product");
        requestDTO.setDescription("Updated Description");
        requestDTO.setImageURL("http://test.com/updated.jpg");

        CategoryRequestDTO categoryDTO = new CategoryRequestDTO();
        categoryDTO.setTitle("Electronics");
        requestDTO.setCategory(categoryDTO);

        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setTitle("Updated Product");

        when(productService.updateProduct(any(UpdateProductrequestDTO.class))).thenReturn(updatedProduct);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Product", response.getBody().getTitle());
    }

    @Test
    void updateProduct_ThrowsException_WhenIdIsNull() {
        // Arrange
        UpdateProductrequestDTO requestDTO = new UpdateProductrequestDTO();
        requestDTO.setTitle("Updated Product");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productController.updateProduct(requestDTO));
    }

    @Test
    void deleteProductById_ReturnsDeletedProduct() throws ProductNotFoundException {
        // Arrange
        when(productService.deleteProductById(1)).thenReturn(sampleProduct);

        // Act
        ResponseEntity<Product> response = productController.deleteProductById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void deleteProductById_ThrowsException_WhenInvalidId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productController.deleteProductById(-1));
    }

    @Test
    void deleteProductById_ThrowsException_WhenProductNotFound() throws ProductNotFoundException {
        // Arrange
        when(productService.deleteProductById(anyInt())).thenReturn(null);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productController.deleteProductById(100));
    }
}