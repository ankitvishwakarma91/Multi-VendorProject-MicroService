package com.softworkshub.orderservice.model;


import com.softworkshub.orderservice.util.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // Reference to User-Service
    private Double totalAmount;
    private Status status; // PLACED, SHIPPED, DELIVERED, CANCELLED
    private Integer quantity;
    private Long productId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
