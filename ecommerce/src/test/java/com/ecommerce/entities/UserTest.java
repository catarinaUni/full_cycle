package com.ecommerce.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    private User user;
    private Product product;
    private Payment payment;

    @BeforeEach
    void setUp() {
        user = new User("Alice", "alice@example.com", "securepass", "Rua 123", new ArrayList<>());
        product = new Product("Laptop", "High-end laptop", BigDecimal.valueOf(3000), "Electronics", user, "image.jpg", BigDecimal.valueOf(4.5));
        payment = mock(Payment.class);
    }

    @Test
    void testUserRegistration() {
        User registeredUser = User.register("John", "john@example.com", "pass123", "Rua 456", new ArrayList<>());
        assertNotNull(registeredUser);
        assertEquals("John", registeredUser.getName());
        assertEquals("john@example.com", registeredUser.getEmail());
    }

    @Test
    void testUserLoginSuccess() {
        assertTrue(user.login("alice@example.com", "securepass"));
    }

    @Test
    void testUserLoginFailure() {
        assertFalse(user.login("alice@example.com", "wrongpass"));
    }

    @Test
    void testAddProductToSale() {
        user.addProductToSale("Smartphone", "Latest model", BigDecimal.valueOf(2000), "Electronics", user, "phone.jpg", BigDecimal.valueOf(4.8));
        assertEquals(1, user.getProductsForSale().size());
    }

    @Test
    void testAddToCart() {
        user.addToCart(product, 2);
        assertEquals(1, user.getCartItems().size());
        assertEquals(2, user.getCartItems().get(0).getQuantity());
    }

    @Test
    void testBuySuccess() {
        when(payment.processPayment()).thenReturn(true);
        user.addToCart(product, 1);

        user.buy(payment);

        assertEquals(0, user.getCartItems().size());
    }

}
