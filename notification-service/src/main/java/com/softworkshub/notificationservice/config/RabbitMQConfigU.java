package com.softworkshub.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigU {

    private static final String EXCHANGE_NAME = "direct-exchange";
    private static final String ROUTING_KEY_USER_LOGIN = "user.login";
    private static final String ROUTING_KEY_USER_REGISTER = "user.register";
    public static final String ROUTING_KEY_ORDER_PLACED = "place-order";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue userLoginQueue() {
        return new Queue("login-queue");
    }

    @Bean
    public Queue userRegisterQueue() {
        return new Queue("register-queue");
    }
    @Bean
    public Queue getQueue() {
        return new Queue("place-order");
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder.bind(getQueue()).to(directExchange()).with(ROUTING_KEY_ORDER_PLACED);
    }

    @Bean
    public Binding userLoginBinding() {
        return BindingBuilder.bind(userLoginQueue()).to(directExchange()).with(ROUTING_KEY_USER_LOGIN);
    }
    @Bean
    public Binding userRegisterBinding() {
        return BindingBuilder.bind(userRegisterQueue()).to(directExchange()).with(ROUTING_KEY_USER_REGISTER);
    }
}
