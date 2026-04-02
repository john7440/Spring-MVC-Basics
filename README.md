# SmartZone
 
A Spring Boot MVC web application for managing an e-commerce shop (articles, categories, orders and users),  
developed as a Spring Boot / JPA / Thymeleaf training exercise
 
## Table of Contents
 
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technologies](#technologies)

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
