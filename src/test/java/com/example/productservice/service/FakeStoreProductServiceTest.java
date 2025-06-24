package com.example.productservice.service;

import com.example.productservice.dto.ResponseDTO;
import com.example.productservice.dto.UpdateProductrequestDTO;
import com.example.productservice.model.Category;
import com.example.productservice.model.Product;
import com.example.productservice.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FakeStoreProductServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FakeStoreProductService fakeStoreProductService;

    private ResponseDTO sampleResponseDTO;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        // Initialize sample ResponseDTO
        sampleResponseDTO = new ResponseDTO();
        sampleResponseDTO.setId(1);
        sampleResponseDTO.setTitle("Test Product");
        sampleResponseDTO.setDescription("Test Description");
        sampleResponseDTO.setImage("http://test.com/image.jpg");
        sampleResponseDTO.setCategory("Electronics");
        sampleResponseDTO.setPrice(99.99);

        // Initialize sample Product
        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setTitle("Test Product");
        sampleProduct.setDescription("Test Description");
        sampleProduct.setImageURL("http://test.com/image.jpg");

        Category category = new Category();
        category.setTitle("Electronics");
        sampleProduct.setCategory(category);
    }

    @Test
    void getProductById_ReturnsProduct_WhenValidId() {
        // Arrange
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(sampleResponseDTO, HttpStatus.OK);
        when(restTemplate.getForEntity("https://fakestoreapi.com/products/1", ResponseDTO.class))
                .thenReturn(responseEntity);

        // Act
        Product result = fakeStoreProductService.getProductById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals("http://test.com/image.jpg", result.getImageURL());
        assertEquals("Electronics", result.getCategory().getTitle());
        verify(restTemplate).getForEntity("https://fakestoreapi.com/products/1", ResponseDTO.class);
    }

    @Test
    void getProductById_ThrowsException_WhenResponseIsNull() {
        // Arrange
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity("https://fakestoreapi.com/products/1", ResponseDTO.class))
                .thenReturn(responseEntity);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fakeStoreProductService.getProductById(1));
    }

    @Test
    void getProductById_ThrowsException_WhenServerError() {
        // Arrange
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity("https://fakestoreapi.com/products/1", ResponseDTO.class))
                .thenReturn(responseEntity);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> fakeStoreProductService.getProductById(1));
    }

    @Test
    void getAllProducts_ReturnsProductList() {
        // Arrange
        ResponseDTO[] responseDTOs = new ResponseDTO[]{sampleResponseDTO};
        ResponseEntity<ResponseDTO[]> responseEntity = new ResponseEntity<>(responseDTOs, HttpStatus.OK);
        when(restTemplate.getForEntity("https://fakestoreapi.com/products", ResponseDTO[].class))
                .thenReturn(responseEntity);

        // Act
        List<Product> result = fakeStoreProductService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getTitle());
        verify(restTemplate).getForEntity("https://fakestoreapi.com/products", ResponseDTO[].class);
    }

    @Test
    void getPaginatedProducts_ReturnsNull() {
        // Act
        assertNull(fakeStoreProductService.getPaginatedProducts(1, 10));
    }

    @Test
    void createProduct_ReturnsCreatedProduct() {
        // Arrange
        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(sampleResponseDTO, HttpStatus.CREATED);
        when(restTemplate.postForEntity(eq("https://fakestoreapi.com/products"), any(ResponseDTO.class), eq(ResponseDTO.class)))
                .thenReturn(responseEntity);

        // Act
        Product result = fakeStoreProductService.createProduct("Test Product", "http://test.com/image.jpg", "Electronics", "Test Description");

        // Assert
        assertNotNull(result);
        assertEquals("Test Product", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals("Electronics", result.getCategory().getTitle());
        verify(restTemplate).postForEntity(eq("https://fakestoreapi.com/products"), any(ResponseDTO.class), eq(ResponseDTO.class));
    }

    @Test
    void updateProduct_ReturnsUpdatedProduct() {
        // Arrange
        ResponseEntity<ResponseDTO> getResponseEntity = new ResponseEntity<>(sampleResponseDTO, HttpStatus.OK);
        when(restTemplate.getForEntity("https://fakestoreapi.com/products/1", ResponseDTO.class))
                .thenReturn(getResponseEntity);

        ResponseEntity<ResponseDTO> updateResponseEntity = new ResponseEntity<>(sampleResponseDTO, HttpStatus.OK);
        when(restTemplate.exchange(eq("https://fakestoreapi.com/products/1"), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(ResponseDTO.class)))
                .thenReturn(updateResponseEntity);

        UpdateProductrequestDTO requestDTO = new UpdateProductrequestDTO();
        requestDTO.setId(1);
        requestDTO.setTitle("Updated Product");

        Category category = new Category();
        category.setTitle("Electronics");
        requestDTO.setCategory(category);

        // Act
        Product result = fakeStoreProductService.updateProduct(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getTitle()); // Using the response from the mock
        verify(restTemplate).exchange(eq("https://fakestoreapi.com/products/1"), eq(HttpMethod.PATCH), any(HttpEntity.class), eq(ResponseDTO.class));
    }

    @Test
    void updateProduct_ThrowsException_WhenInvalidId() {
        // Arrange
        UpdateProductrequestDTO requestDTO = new UpdateProductrequestDTO();
        requestDTO.setId(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> fakeStoreProductService.updateProduct(requestDTO));
    }

    @Test
    void deleteProductById_ReturnsDeletedProduct() {
        // Arrange
        ResponseEntity<ResponseDTO> getResponseEntity = new ResponseEntity<>(sampleResponseDTO, HttpStatus.OK);
        when(restTemplate.getForEntity("https://fakestoreapi.com/products/1", ResponseDTO.class))
                .thenReturn(getResponseEntity);

        // Act
        Product result = fakeStoreProductService.deleteProductById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Product", result.getTitle());
        verify(restTemplate).delete("https://fakestoreapi.com/products/1");
    }
}