package com.ecommerce.controllers;

import com.ecommerce.entities.Cart;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartController(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Integer userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Integer userId, @RequestParam Integer productId, @RequestParam Integer quantity) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);

        if (user.isEmpty() || product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        cart.setUser(user.get());
        cart.addItem(product.get(), quantity);

        cartRepository.save(cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Integer userId, @RequestParam Integer productId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        Optional<Product> product = productRepository.findById(productId);

        if (cart.isEmpty() || product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cart.get().removeItem(product.get());
        cartRepository.save(cart);
        return ResponseEntity.ok(cart.get());
    }

    @PostMapping("/{userId}/buy")
    public ResponseEntity<String> checkout(@PathVariable Integer userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);

        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cart.get().buy(null);
        cartRepository.save(cart.get());
        return ResponseEntity.ok("Compra realizada com sucesso!");
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Integer userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cart.get().clearCart();
        cartRepository.save(cart.get());
        return ResponseEntity.ok("Carrinho esvaziado com sucesso!");
    }
}
