package com.example.productservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        // This method is executed before each test.
        // It's used to set up the initial state for the tests.
        category = new Category();
    }

    @Test
    void testGettersAndSetters() {
        // This test case verifies that the getters and setters of the Category class work as expected.

        // Arrange: Set the values for the properties of the Category object.
        category.setId(1);
        category.setTitle("Electronics");
        category.setDeleted(false);

        // Assert: Check if the getters return the values that were set.
        assertEquals(1, category.getId());
        assertEquals("Electronics", category.getTitle());
        assertFalse(category.isDeleted());
    }

    @Test
    void testDefaultConstructor() {
        // This test case verifies that the default constructor creates a non-null object.

        // Assert: Check that the created object is not null.
        assertNotNull(category);
    }
}
