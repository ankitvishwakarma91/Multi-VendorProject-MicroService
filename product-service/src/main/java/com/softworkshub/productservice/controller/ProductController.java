package com.softworkshub.productservice.controller;

import com.softworkshub.productservice.dto.ProductAdd;
import com.softworkshub.productservice.model.Product;
import com.softworkshub.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Email") String email,
            @RequestBody ProductAdd product
    ) {
        if (role.equals("SELLER") || role.equals("ADMIN")) {
            return productService.addProduct(product,email);
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getById")
    public Product getProductById(@RequestParam("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/updateQuantity")
    public Product updateQuantityByProductId(@RequestParam Long productId,
                                             @RequestParam Integer quantity) {
        return productService.updateQuantityByProductId(productId, quantity);
    }
}
