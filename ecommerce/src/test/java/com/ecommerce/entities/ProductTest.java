package com.ecommerce.entities;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;


class ProductTest {

    @Test
    void testDefaultConstructor() {
        Product product = new Product();
        assertNotNull(product.getCreateAt());
        assertNull(product.getName());
        assertNull(product.getDescription());
        assertNull(product.getPrice());
        assertNull(product.getCategory());
        assertNull(product.getAuthorship());
        assertNull(product.getImageUrl());
        assertNull(product.getRating());
    }

    @Test
void testParameterizedConstructor() {
    User user = new User(); // Assuming User is a valid class
    Date beforeCreation = new Date();
    Product product = new Product(
            "Laptop",
            "High-end gaming laptop",
            new BigDecimal("1500.00"),
            "Electronics",
            user,
            "http://example.com/image.jpg",
            new BigDecimal("4.5")
    );
    Date afterCreation = new Date();

    assertEquals("Laptop", product.getName());
    assertEquals("High-end gaming laptop", product.getDescription());
    assertEquals(new BigDecimal("1500.00"), product.getPrice());
    assertEquals("Electronics", product.getCategory());
    assertEquals(user, product.getAuthorship());
    assertEquals("http://example.com/image.jpg", product.getImageUrl());
    assertEquals(new BigDecimal("4.5"), product.getRating());
    assertNotNull(product.getCreateAt());

    // Allowing a small margin for time comparison (e.g., 1 second)
    long timeDifference = product.getCreateAt().getTime() - beforeCreation.getTime();
    long timeAfter = afterCreation.getTime() - product.getCreateAt().getTime();

    // Check that the product creation time is within the allowed margin
    assertTrue(timeDifference >= 0 && timeAfter >= 0, "The product creation time is not between before and after.");
}
}