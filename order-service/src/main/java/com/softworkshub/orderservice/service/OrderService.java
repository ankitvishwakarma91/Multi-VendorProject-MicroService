package com.softworkshub.orderservice.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softworkshub.orderservice.client.ProductClient;
import com.softworkshub.orderservice.config.RabbitMQConfig;
import com.softworkshub.orderservice.dto.OrderResponse;
import com.softworkshub.orderservice.model.Order;
import com.softworkshub.orderservice.model.Product;
import com.softworkshub.orderservice.repository.OrderRepository;
import com.softworkshub.orderservice.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;


    public OrderResponse placeOrder(
            String email,
            Long productId,
            Integer quantity
    ) throws JsonProcessingException {

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

        Map<String,String> payload = new HashMap<>();
        payload.put("to",email);
        payload.put("subject","Order-Placed Notification");
        payload.put("message","Your Order has been placed" + productById.getProductName() + "Price " + productById.getProductPrice());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_ORDER_PLACED,
                new ObjectMapper().writeValueAsString(payload)
        );

        return orderResponse;
    }



}
