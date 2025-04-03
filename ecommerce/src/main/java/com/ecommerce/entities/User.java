package com.ecommerce.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createAt;

    @OneToMany(mappedBy = "authorship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productsForSale = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public User() {
        this.createAt = new Date();
    }

    public User(String name, String email, String password, String address, List<Product> productsForSale) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.createAt = new Date();
        this.productsForSale = productsForSale;
    }

    public static User register(String name, String email, String password, String address, List<Product> productsForSale) {
        return new User(name, email, password, address, productsForSale);
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void addProductToSale(String name, String description, BigDecimal price, String category,
                                 User authorship, String imageUrl, BigDecimal rating) {
        Product product = new Product(name, description, price, category, authorship, imageUrl, rating);
        this.productsForSale.add(product);
    }

    public void removeProductFromSale(Product product) {
        this.productsForSale.remove(product);
    }
    public void updateProduct(Product product, String name, String description, BigDecimal price, String category,
                              String imageUrl, BigDecimal rating) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        product.setRating(rating);
    }

    public void addToCart(Product product, int quantity) {
        CartItem cartItem = new CartItem(product, quantity);
        cartItems.add(cartItem);
    }

    public void buy(Payment payment) {
        BigDecimal totalPrice = calculateTotalPrice();
        Order order = new Order(this, new ArrayList<>(), totalPrice);
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getPrice());
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        if (payment.processPayment()) {
            order.setOrderStatus(OrderStatus.COMPLETE);
            System.out.println("Pedido realizado com sucesso!");
        } else {
            order.setOrderStatus(OrderStatus.FAILED);
            System.out.println("Falha no pagamento. Tente novamente.");
        }

        cartItems.clear();
    }

    private BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemTotal = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);
        }
        return totalPrice;
    }
}