package com.softworkshub.orderservice.dto;


import lombok.Data;

@Data
public class OrderRequestDto {

    private Long productId;

    private Integer quantity;
}
