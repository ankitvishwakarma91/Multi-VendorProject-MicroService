package com.softworkshub.userservices.client;

import com.softworkshub.userservices.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PRODUCT-SERVICE")
public interface ProductClient {

    @PostMapping("/api/v1/product/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product);
}
