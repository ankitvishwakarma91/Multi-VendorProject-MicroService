package com.softworkshub.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softworkshub.notificationservice.model.NotificationModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public NotificationService(EmailService emailService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "register-queue")
    public void handleRegisterListener(String message){
        try {
            NotificationModel model = objectMapper.readValue(message, NotificationModel.class);
            emailService.sendEmail(model.getTo(),model.getSubject(),model.getMessage());
            System.out.println("Register email sent" + model.getTo());
        } catch (Exception e) {
            System.err.println("Error processing register email: " + e.getMessage());
        }
    }


    @RabbitListener(queues = "login-queue")
    public void handleLoginListener(String message){
        try {
            NotificationModel model = objectMapper.readValue(message, NotificationModel.class);
            emailService.sendEmail(model.getTo(),model.getSubject(),model.getMessage());
            System.out.println("Login email sent" + model.getTo());
        } catch (Exception e) {
            System.err.println("Error processing register email: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "place-order")
    public void handleOrderPlacedListener(String message){
        try {
            NotificationModel model = objectMapper.readValue(message, NotificationModel.class);
            emailService.sendEmail(model.getTo(),model.getSubject(),model.getMessage());
            System.out.println("Order has been placed to this id : " + model.getTo());
        } catch (Exception e) {
            System.err.println("Error processing register email: " + e.getMessage());
        }
    }
}
