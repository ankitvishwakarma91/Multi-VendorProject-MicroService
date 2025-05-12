package com.softworkshub.orderservice.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "direct-exchange";
    public static final String ROUTING_KEY_ORDER_PLACED = "place-order";


    @Bean
    public DirectExchange getExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getQueue() {
        return new Queue("place-order");
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder.bind(getQueue()).to(getExchange()).with(ROUTING_KEY_ORDER_PLACED);
    }
}
