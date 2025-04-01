package com.ecommerce.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ReviewTest {

    private User mockUser;
    private Product mockProduct;
    private Review review;

    @BeforeEach
    void setUp() {
        mockUser = mock(User.class);
        mockProduct = mock(Product.class);

        when(mockUser.getName()).thenReturn("John Doe");
        when(mockProduct.getName()).thenReturn("Sample Product");

        review = new Review(mockUser, mockProduct, 5, "Great product!");
    }

    @Test
    void testDefaultConstructor() {
        Review defaultReview = new Review();
        assertNotNull(defaultReview.getCreatedAt());
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals(mockUser, review.getUser());
        assertEquals(mockProduct, review.getProduct());
        assertEquals(5, review.getRating());
        assertEquals("Great product!", review.getComment());
        assertNotNull(review.getCreatedAt());
    }

    @Test
    void testEditReview() {
        review.editReview(4, "Good product!");
        assertEquals(4, review.getRating());
        assertEquals("Good product!", review.getComment());
    }

    @Test
    void testGetReviewDetails() {
        String details = review.getReviewDetails();
        assertTrue(details.contains("Usuário: John Doe"));
        assertTrue(details.contains("Produto: Sample Product"));
        assertTrue(details.contains("Nota: 5"));
        assertTrue(details.contains("Comentário: Great product!"));
        assertTrue(details.contains("Criado em:"));
    }

    @Test
    void testCreatedAtIsSet() {
        Date now = new Date();
        assertTrue(review.getCreatedAt().before(new Date(now.getTime() + 1000)));
    }
}