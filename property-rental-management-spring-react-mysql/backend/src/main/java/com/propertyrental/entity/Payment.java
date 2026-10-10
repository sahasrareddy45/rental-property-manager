package com.propertyrental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
public class Payment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="rental_request_id", nullable=false)
    private RentalRequest rentalRequest;

    private Double amount;
    private String paymentMethod;
    private String paymentStatus = "PAID";
    private LocalDateTime paidAt = LocalDateTime.now();

    public Payment() {}

    public Long getId(){return id;}
    public RentalRequest getRentalRequest(){return rentalRequest;}
    public void setRentalRequest(RentalRequest v){rentalRequest=v;}
    public Double getAmount(){return amount;}
    public void setAmount(Double v){amount=v;}
    public String getPaymentMethod(){return paymentMethod;}
    public void setPaymentMethod(String v){paymentMethod=v;}
    public String getPaymentStatus(){return paymentStatus;}
    public LocalDateTime getPaidAt(){return paidAt;}
}
