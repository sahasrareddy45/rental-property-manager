package com.propertyrental.entity;

import jakarta.persistence.*;

@Entity
@Table(name="properties")
public class Property {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, length=2000)
    private String description;

    @Column(nullable=false)
    private String location;

    @Column(nullable=false)
    private Double monthlyRent;

    @Column(nullable=false)
    private String propertyType;

    private Integer bedrooms;
    private Integer bathrooms;
    private String imageUrl;
    private boolean available = true;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.PENDING;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

    public Property() {}

    public Long getId(){return id;}
    public String getTitle(){return title;}
    public void setTitle(String v){title=v;}
    public String getDescription(){return description;}
    public void setDescription(String v){description=v;}
    public String getLocation(){return location;}
    public void setLocation(String v){location=v;}
    public Double getMonthlyRent(){return monthlyRent;}
    public void setMonthlyRent(Double v){monthlyRent=v;}
    public String getPropertyType(){return propertyType;}
    public void setPropertyType(String v){propertyType=v;}
    public Integer getBedrooms(){return bedrooms;}
    public void setBedrooms(Integer v){bedrooms=v;}
    public Integer getBathrooms(){return bathrooms;}
    public void setBathrooms(Integer v){bathrooms=v;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String v){imageUrl=v;}
    public boolean isAvailable(){return available;}
    public void setAvailable(boolean v){available=v;}
    public PropertyStatus getStatus(){return status;}
    public void setStatus(PropertyStatus v){status=v;}
    public User getOwner(){return owner;}
    public void setOwner(User v){owner=v;}
}
