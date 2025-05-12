package com.softworkshub.orderservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.softworkshub.orderservice.dto.OrderResponse;
import com.softworkshub.orderservice.model.Order;
import com.softworkshub.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    // You have to work on Order Service Get product details from product service

    @Autowired
    private OrderService orderService;


    @GetMapping("/test")
    public String test(){
        return "ALL OK FROM ORDER SERVICE";
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestHeader("X-User-Email") String email,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) throws JsonProcessingException {
        OrderResponse orderResponse = orderService.placeOrder(email, productId, quantity);
        return new ResponseEntity<>(orderResponse,HttpStatus.CREATED);
    }

}
