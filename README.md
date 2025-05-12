## MultiVendor Project

# 🛒 Multivendor E-commerce System

This project is a modular **multivendor e-commerce platform** built using **Spring Boot**, **Spring Security**, **Spring Cloud Gateway**, and **RabbitMQ**. It demonstrates a microservices architecture that separates user management, product catalog, ordering, and notification functionalities across dedicated services.

---

## 🧩 Modules

### 🔐 **User Service**
- **Description**: Manages user registration and login functionality.
- **Tech Stack**: Spring Boot, Spring Security, Spring Data JPA, MySQL

### Register User 
```bash
POST  http://localhost:8084/api/v1/auth/register
```
---

### 🛍️ **Product Service**
- **Description**: Handles product catalog management.
  - All users can view products.
  - Only admins and sellers can add new products.
- **Tech Stack**: Spring Boot, Spring Data JPA, PostgreSQL, Spring Security

---

### 📦 **Order Service**
- **Description**: Processes product orders and sends order events to the message broker.
- **Tech Stack**: Spring Boot, Spring Security, RabbitMQ, Spring Data JPA, PostgreSQL

---

### 📣 **Notification Service**
- **Description**: Listens to user and order events and sends email notifications for:
  - User login  
  - User registration  
  - Order placement
- **Tech Stack**: Spring Boot, RabbitMQ, Email Integration

---

### 🌐 **API Gateway**
- **Description**: Central entry point that routes requests to internal services (User, Product, Order).
- **Tech Stack**: Spring Boot, Spring Cloud Gateway

---

## ✅ Features
- JWT-based Authentication & Authorization
- Role-based Access Control (Admin, Seller, Customer)
- Event-driven communication using RabbitMQ
- RESTful APIs for all core services
- MySQL and PostgreSQL database integration
- Scalable Microservices Architecture

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- RabbitMQ Server
- MySQL & PostgreSQL databases
- Spring Cloud Config Server (if using central configuration)

### Run Services
Each service can be run individually using:

```bash
mvn spring-boot:run
