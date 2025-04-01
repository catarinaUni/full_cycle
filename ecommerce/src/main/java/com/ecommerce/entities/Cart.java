package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal totalPrice;

    public Cart() {
        this.totalPrice = BigDecimal.ZERO;
    }

    public void addItem(Product product, Integer quantity) {
        CartItem existingItem = findItemByProduct(product);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(product, quantity);
            cartItems.add(newItem);
        }
        calculateTotalPrice();
    }

    public void removeItem(Product product) {
        CartItem itemToRemove = findItemByProduct(product);
        if (itemToRemove != null) {
            cartItems.remove(itemToRemove);
            calculateTotalPrice();
        }
    }

    private CartItem findItemByProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                return item;
            }
        }
        return null;
    }

    public void calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        this.totalPrice = total;
    }

    public void buy(Payment payment) {
        if (cartItems.isEmpty()) {
            System.out.println("Carrinho vazio. Adicione itens ao carrinho antes de realizar a compra.");
            return;
        }
        Order order = new Order(user, new ArrayList<>(), totalPrice);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getPrice());
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.placeOrder();
        clearCart();

        if (payment != null) {
            System.out.println("Pagamento processado com sucesso!");
        } else {
            System.out.println("Falha no pagamento. Tente novamente.");
        }
    }

    public void clearCart() {
        cartItems.clear();
        totalPrice = BigDecimal.ZERO;
    }

}
