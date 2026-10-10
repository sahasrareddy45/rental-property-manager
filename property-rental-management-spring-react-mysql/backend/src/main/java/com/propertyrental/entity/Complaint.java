package com.propertyrental.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="complaints")
public class Complaint {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(nullable=false)
    private String subject;

    @Column(nullable=false, length=2000)
    private String description;

    private String status = "OPEN";
    private LocalDateTime createdAt = LocalDateTime.now();

    public Complaint() {}

    public Long getId(){return id;}
    public User getUser(){return user;}
    public void setUser(User v){user=v;}
    public String getSubject(){return subject;}
    public void setSubject(String v){subject=v;}
    public String getDescription(){return description;}
    public void setDescription(String v){description=v;}
    public String getStatus(){return status;}
    public void setStatus(String v){status=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
