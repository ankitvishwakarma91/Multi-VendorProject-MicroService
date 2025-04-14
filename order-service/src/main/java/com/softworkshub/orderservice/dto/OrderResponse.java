package com.softworkshub.orderservice.dto;


import com.softworkshub.orderservice.model.Product;
import com.softworkshub.orderservice.util.Status;
import lombok.Data;

@Data
public class OrderResponse {

    private Long orderId;
    private Product product;
    private Integer quantity;
    private Status status ;

}
