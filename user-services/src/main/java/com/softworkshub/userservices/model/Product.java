package com.softworkshub.userservices.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false,length = 200)
    private String productDescription;

    @Column(nullable = false)
    private String productCategory;

    @Column(nullable = false)
    private Double productPrice;

    @Column(nullable = false)
    private String productImageUrl;

    @Column(nullable = false)
    private int quantity;

    private String sellerId; // Reference to User-Service

    @CreationTimestamp
    private LocalDateTime createdAt;
}
