package com.softworkshub.productservice.service;

import com.softworkshub.productservice.dto.ProductAdd;
import com.softworkshub.productservice.model.Product;
import com.softworkshub.productservice.repo.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Product> addProduct(ProductAdd productAdd , String email) {
        Product product = new Product();
        product.setProductName(productAdd.getProductName());
        product.setProductDescription(productAdd.getProductDescription());
        product.setProductPrice(productAdd.getProductPrice());
        product.setProductCategory(productAdd.getProductCategory());
        product.setProductImageUrl(productAdd.getProductImageUrl());
        product.setQuantity(productAdd.getQuantity());
        product.setSellerId(email);
        Product save = productRepository.save(product);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }


    public Product updateQuantityByProductId(Long id, Integer quantity) {
        Optional<Product> byId = productRepository.findById(id);

        if (byId.isPresent()){
            Product originalProduct = byId.get();

            int originalQuantity = originalProduct.getQuantity();

            if (originalQuantity >= quantity){
                int updatedQuantity = originalQuantity - quantity;

                originalProduct.setQuantity(updatedQuantity);
            }else {
                throw new RuntimeException("This much Quantity is not available " + quantity);
            }
            return productRepository.save(originalProduct);
        }
        throw new RuntimeException("Product not found with ID: " + id);
    }

}
