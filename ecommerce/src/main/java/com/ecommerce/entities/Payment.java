package com.ecommerce.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
public class Payment {

    @Id
    private Integer id;

    @OneToOne
    private Order order;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    public Payment() {
        this.paymentDate = new Date();
    }

    public Payment(Order order, BigDecimal amount, PaymentStatus status, PaymentMethod paymentMethod) {
        this.order = order;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentDate = new Date();
    }

    public boolean processPayment() {
        if (this.status == PaymentStatus.PENDING) {
            boolean paymentSuccessful = Math.random() > 0.2;

            if (paymentSuccessful) {
                this.status = PaymentStatus.COMPLETED;
                System.out.println("Pagamento processado com sucesso!");
            } else {
                this.status = PaymentStatus.FAILED;
                System.out.println("Falha no processamento do pagamento.");
                return false;
            }
        } else {
            System.out.println("O pagamento já foi processado ou está cancelado.");
        }
        return true;
    }

    public void refundPayment() {
        if (this.status == PaymentStatus.COMPLETED) {
            this.status = PaymentStatus.REFUNDED;
            System.out.println("Pagamento reembolsado com sucesso.");
        } else {
            System.out.println("O pagamento não pode ser reembolsado.");
        }
    }

    public String getPaymentDetails() {
        return String.format("Pedido: %d | Valor: %.2f | Método: %s | Status: %s | Data: %s",
                order.getId(), amount, paymentMethod, status, paymentDate.toString());
    }
}
