# Shop-MVC-Basics
 
A Spring Boot MVC web application for managing an e-commerce shop (articles, categories, orders and users),  
developed as a Spring Boot / JPA / Thymeleaf training exercise
 
## Table of Contents
 
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Usage](#usage)
- [Project Structure](#project-structure)

## Overview
 
SmartZone is a web-based e-commerce platform built with Spring Boot and Thymeleaf.  
It provides a full article and category management system for administrators,  
and a shopping experience with a localStorage-based cart and order history for users  
Note: Authentication is handled via HTTP session with BCrypt password hashing

## Features
 
### Article Management (Admin)
- Display all articles with pagination and keyword search
- Filter articles by category
- Add a new article with category association
- Edit an existing article
- Delete an article
 
### Category Management (Admin)
- Display all categories
- Add a new category
- Edit an existing category inline
- Delete a category
 
###  User Management
- Register a new account
- Login / Logout with session management
- BCrypt password hashing
- Role-based access control (`USER` / `ADMIN`)
 
### Shopping Cart
- Add articles to cart (localStorage)
- Adjust quantities or remove items
- View cart total in real time
- Cart badge updated 
 
### Order Management
- Place an order from the cart (persisted to database)
- view order history with details
- Order confirmation page

## Architecture
 
The application follows a **multi-layered MVC architecture**:
 
- **View Layer** (Thymeleaf + Bootstrap 5) - Html templates with layout inheritance
- **Controller Layer** - Spring MVC `@Controller` and `@RestController`
- **Repository Layer** - Database access via `JpaRepository`
- **Entity Layer** - JPA-mapped objects
- **Security** - Session-based auth + BCrypt via Spring Security
- **Database** - MariaDB
 
## Technologies
 
- **Language**: Java 17
- **Framework**: Spring Boot 4.0.5
- **View**: Thymeleaf + Thymeleaf Layout Dialect
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security (BCrypt, session-based auth)
- **Database**: MariaDB
- **Frontend**: Bootstrap 5,bootstrap icons, vanilla JS (localStorage cart)
- **Build Tool**: Maven
- **IDE**: IntelliJ IDEA

## Installation
 
### 1. Clone the repository
```bash
git clone https://github.com/john7440/Spring-MVC-Basics.git
```
 
### 2. Open in IntelliJ IDEA
1. `File` -> `Open` -> Select the project folder
2. Wait for IntelliJ to index the project and download Maven dependencies
3. Verify that `pom.xml` is correctly recognized

## Database Setup
 
The database configuration is located in `src/main/resources/application.properties`:
 
```properties
# Database
spring.datasource.url=jdbc:mariadb://localhost:3308/stockmvc?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
 
# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```
**Note**: Update credentials if your MariaDB setup uses a different port, username, or password

### Creating the first Admin account
 
On first run, visit `/createAdmin` to generate the default admin account:
 
| Field    | Value      |
|----------|------------|
| Username | `admin`    |
| Password | `admin123` |
| Role     | `ADMIN`    |
 
 - **REMOVE** the `/createAdmin` route from `UserController` after first use !

## Usage
 
### Run the application
 
**Option A — IntelliJ IDEA:**
 
Navigate to `src/main/java/com/example/Application.java`  
Right-click -> `Run 'Application.main()'`
 
**Option B — Maven:**
```bash
mvn spring-boot:run
```
 
Then open your browser at: [http://localhost:8080/index](http://localhost:8080/index)
 
### Navigation
 
| Route                  | Access       | Description                        |
|------------------------|--------------|------------------------------------|
| `/index`               | All          | Article list with search & filters |
| `/formArticle`         | Admin        | Add a new article                  |
| `/editArticle?id=x`    | Admin        | Edit an existing article           |
| `/delete?id=x`         | Admin        | Delete an article                  |
| `/admin/categories`    | Admin        | Manage categories                  |
| `/cart`                | All          | View shopping cart                 |
| `/order` (POST)        | User         | Place an order                     |
| `/orders`              | User         | View order history                 |
| `/orderConfirmation`   | User         | Order confirmation page            |
| `/login`               | All          | Login form                         |
| `/register`            | All          | Registration form                  |
| `/logout`              | Logged in    | Invalidate session                 |

## Project Structure
 
```text
Shop-Spring-MVC/
├── src/
│   └── main/
│       ├── java/com/example/
│       │   ├── Application.java                  # Entry point
│       │   ├── config/
│       │   │   └── SecurityConfig.java           # BCrypt + Security filter chain
│       │   ├── dao/
│       │   │   ├── ArticleRepository.java
│       │   │   ├── CategoryRepository.java
│       │   │   ├── AppUserRepository.java
│       │   │   ├── OrderRepository.java
│       │   │   └── OrderItemRepository.java
│       │   ├── entities/
│       │   │   ├── Article.java
│       │   │   ├── Category.java
│       │   │   ├── AppUser.java
│       │   │   ├── Order.java
│       │   │   └── OrderItem.java
│       │   ├── utils/
│       │   │   └── SessionUtils.java             
│       │   └── web/
│       │       ├── ArticleController.java        # Article CRUD
│       │       ├── CategoryController.java       # Category CRUD (admin)
│       │       ├── UserController.java           # login, register, logout
│       │       ├── UserOrderController.java      # Order history + confirmation
│       │       └── OrderController.java          # rest - place order
│       └── resources/
│           ├── templates/
│           │   ├── layout.html                   # base layout (navbar + footer)
│           │   ├── articles.html                 # Article list
│           │   ├── formArticle.html              # Add / edit article
│           │   ├── categories.html               # Category management
│           │   ├── cart.html                     # Shopping cart (localStorage)
│           │   ├── orders.html                   # Order history
│           │   ├── orderConfirmation.html        # Order confirmation
│           │   ├── login.html                    # Login form
│           │   └── register.html                 # Registration form
│           ├── static/
│           │   └── css/
│           │       └── bootstrap.min.css
│           └── application.properties
├── pom.xml
└── README.md
```

## License
 
This project is part of a Spring Boot / JPA / Thymeleaf training exercise and is for educational purposes only  
© 2026 [Jonathan Maier](https://github.com/john7440)
