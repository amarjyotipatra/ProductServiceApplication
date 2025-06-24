package com.example.productservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        // This method is executed before each test.
        // It's used to set up the initial state for the tests.
        product = new Product();
    }

    @Test
    void testGettersAndSetters() {
        // This test case verifies that the getters and setters of the Product class work as expected.

        // Arrange: Set the values for the properties of the Product object.
        Category category = new Category();
        category.setTitle("Electronics");

        Date now = new Date();

        product.setId(1);
        product.setTitle("Laptop");
        product.setDescription("A powerful laptop");
        product.setImageURL("http://example.com/laptop.jpg");
        product.setCategory(category);
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        product.setDeleted(false);

        // Assert: Check if the getters return the values that were set.
        assertEquals(1, product.getId());
        assertEquals("Laptop", product.getTitle());
        assertEquals("A powerful laptop", product.getDescription());
        assertEquals("http://example.com/laptop.jpg", product.getImageURL());
        assertEquals(category, product.getCategory());
        assertEquals(now, product.getCreatedAt());
        assertEquals(now, product.getUpdatedAt());
        assertFalse(product.isDeleted());
    }

    @Test
    void testDefaultConstructor() {
        // This test case verifies that the default constructor creates a non-null object.

        // Assert: Check that the created object is not null.
        assertNotNull(product);
    }
}
