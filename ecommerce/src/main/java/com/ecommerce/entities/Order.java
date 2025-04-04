package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    public Order() {
        this.createdAt = new Date();
        this.orderStatus = OrderStatus.PENDING;
    }

    public Order(User user, List<OrderItem> orderItems, BigDecimal totalPrice) {
        this.user = user;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.createdAt = new Date();
        this.orderStatus = OrderStatus.PENDING;
    }

    public void placeOrder() {
        if (orderStatus == OrderStatus.PENDING) {
            this.orderStatus = OrderStatus.PLACED;
            System.out.println("Pedido realizado com sucesso!");
        } else {
            System.out.println("Não é possível realizar o pedido. O status atual é: " + orderStatus);
        }
    }

    public void cancelOrder() {
        if (orderStatus == OrderStatus.PENDING) {
            this.orderStatus = OrderStatus.CANCELLED;
            System.out.println("Pedido cancelado com sucesso.");
        } else {
            System.out.println("Não é possível cancelar o pedido. O status atual é: " + orderStatus);
        }
    }

    public void calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        this.totalPrice = total;
    }
}