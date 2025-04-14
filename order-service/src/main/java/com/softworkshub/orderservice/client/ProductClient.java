package com.softworkshub.orderservice.client;

import com.softworkshub.orderservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-service",url = "http://localhost:8081")
public interface ProductClient {

    @GetMapping("/api/v1/product/getById")
    public Product getProductById(@RequestParam("id") Long id);

    @GetMapping("/api/v1/product/updateQuantity")
    public Product updateQuantityByProductId(@RequestParam Long productId,
                                             @RequestParam Integer quantity);
}
