package com.propertyrental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="rental_requests")
public class RentalRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="property_id", nullable=false)
    private Property property;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="tenant_id", nullable=false)
    private User tenant;

    @Enumerated(EnumType.STRING)
    private RentalStatus status = RentalStatus.PENDING;

    private LocalDateTime requestedAt = LocalDateTime.now();
    private String message;

    public RentalRequest() {}

    public Long getId(){return id;}
    public Property getProperty(){return property;}
    public void setProperty(Property v){property=v;}
    public User getTenant(){return tenant;}
    public void setTenant(User v){tenant=v;}
    public RentalStatus getStatus(){return status;}
    public void setStatus(RentalStatus v){status=v;}
    public LocalDateTime getRequestedAt(){return requestedAt;}
    public String getMessage(){return message;}
    public void setMessage(String v){message=v;}
}
