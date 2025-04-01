package com.ecommerce.entities;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private String category;
    private User authorship;
    private String imageUrl;

    private BigDecimal rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public Product() {
        this.createAt = new Date(); // Ensure createAt is initialized at the time of object creation
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
