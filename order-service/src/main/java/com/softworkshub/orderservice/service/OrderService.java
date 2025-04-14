package com.softworkshub.orderservice.service;



import com.softworkshub.orderservice.client.ProductClient;
import com.softworkshub.orderservice.dto.OrderResponse;
import com.softworkshub.orderservice.model.Order;
import com.softworkshub.orderservice.model.Product;
import com.softworkshub.orderservice.repository.OrderRepository;
import com.softworkshub.orderservice.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;


    public OrderResponse placeOrder(
            String email,
            Long productId,
            Integer quantity
    ) {

        Product productById = productClient.getProductById(productId);

        int available = productById.getQuantity();
        if (available < quantity ) {
            throw new RuntimeException("This much products not available " + quantity);
        }

        productClient.updateQuantityByProductId(productId,quantity);


        Order order = new Order();

        order.setProductId(productId);
        order.setUserId(email);
        order.setTotalAmount(productById.getProductPrice());
        order.setStatus(Status.PENDING);
        order.setQuantity(quantity);
        Order save = orderRepository.save(order);

        OrderResponse orderResponse = new OrderResponse();



        orderResponse.setOrderId(save.getId());
        orderResponse.setStatus(save.getStatus());
        orderResponse.setQuantity(quantity);
        orderResponse.setProduct(productById);


        return orderResponse;
    }



}
