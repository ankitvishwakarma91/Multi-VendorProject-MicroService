## MultiVendor Project

# 🛒 Multivendor E-commerce System

This project is a modular **multivendor e-commerce platform** built using **Spring Boot**, **Spring Security**, **Spring Cloud Gateway**, and **RabbitMQ**. It demonstrates a microservices architecture that separates user management, product catalog, ordering, and notification functionalities across dedicated services.

---

## 🧩 Modules

### 🔐 **User Service**
- **Description**: Manages user registration and login functionality.
- **Tech Stack**: Spring Boot, Spring Security, Spring Data JPA, MySQL

### Register User: 
```bash
POST  http://localhost:8084/api/v1/auth/register
```
### Login User: 
```bash
POST  http://localhost:8084/api/v1/auth/login
```
### Get User by ID:
```bash
GET http://localhost:8084/api/v1/user/get/(our unique id ) like this -> 9a23280a-67cd-4007-90fe-e74d11041ebc
```
### Get All Users ( ONLY ADMIN CAN SEE ALL USERS )
```bash
GET http://localhost:8084/api/v1/user/all
```
---

### 🛍️ **Product Service**
- **Description**: Handles product catalog management.
  - All users can view products.
  - Only admins and sellers can add new products.
- **Tech Stack**: Spring Boot, Spring Data JPA, PostgreSQL, Spring Security

### Add Product ( Only ADMIN & SELLER role can add product ) user can't add  Please provide Jwt token in header 
```bash
POST  localhost:8084/api/v1/product/create
```
### Get All Product  
```bash
GET  http://localhost:8084/api/v1/product/all
```
### Get Product by Id 
```bash
GET  localhost:8084/api/v1/product/getById?id=2
```

---

### 📦 **Order Service**
- **Description**: Processes product orders and sends order events to the message broker.
- **Tech Stack**: Spring Boot, Spring Security, RabbitMQ, Spring Data JPA, PostgreSQL
  
### Order-Placed 
```bash
POST  localhost:8084/api/v1/order/placeOrder?productId=5&quantity=2
```
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
