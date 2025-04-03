package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private String category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "authorship_id", nullable = false)
    private User authorship;

    private String imageUrl;

    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createAt;

    public Product() {
        this.createAt = new Date();
    }

    public Product(String name, String description, BigDecimal price, String category,
                   User authorship, String imageUrl, BigDecimal rating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.authorship = authorship;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.createAt = new Date();
    }
}