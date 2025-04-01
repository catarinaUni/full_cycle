package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Review() {
        this.createdAt = new Date();
    }

    public Review(User user, Product product, Integer rating, String comment) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = new Date();
    }

    public void editReview(Integer newRating, String newComment) {
        this.rating = newRating;
        this.comment = newComment;
        System.out.println("Avaliação editada com sucesso!");
    }

    public String getReviewDetails() {
        return String.format("Usuário: %s | Produto: %s | Nota: %d | Comentário: %s | Criado em: %s",
                user.getName(), product.getName(), rating, comment, createdAt.toString());
    }

}
