# 🛒 E-Commerce API – Spring Boot

A modular and scalable **E-Commerce REST API** built with **Spring Boot**, featuring product management, shopping cart functionality, order processing, and secure user authentication with role-based access.

## 🚀 Features

- 👤 **User Authentication & Role Management**
- 🛍️ **Product Catalog with Categories**
- 🧺 **Shopping Cart & Cart Items**
- 📦 **Order Placement & Order Items**
- 🔐 **Role-Based Authorization**
- 📖 **Interactive Swagger API Docs**

## 🛠️ Tech Stack

- **Java 17**, **Spring Boot**
- **PostgreSQL**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **Lombok**, **MapStruct**
- **Swagger / OpenAPI**

## 📂 Project Structure

```bash
src/main/java/com/example/ecommerce/
├── auth/           # Authentication and JWT logic
├── user/           # User and role management
├── product/        # Product and category handling
├── cart/           # Cart and cart item management
├── order/          # Order and order item processing
└── common/         # Shared enums, DTOs, and utilities
```

## 🧪 Running the Project
```bash
# Clone the repo
git clone https://github.com/Nanret123/ecommerce_spring.git
cd ecommerce_spring

# Build the project
./mvnw clean install

# Run the app
./mvnw spring-boot:run
```

## 📖 API Documentation
Once the app is running, open your browser and visit:

**https://ecommerce-spring-i3hj.onrender.com/swagger-ui.html**

to explore and test available endpoints via Swagger UI.
