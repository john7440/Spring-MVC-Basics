package com.example.dao;

import com.example.entities.Article;
import com.example.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByArticle(Article article);
    List<Order> findByUserId(Long userId);
}
