package com.example.dao;

import com.example.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Page<Article> findByModelContainsAndDeletedFalse(String model,Pageable pageable);
    Page<Article> findByCategoryIdAndDeletedFalse(Long categoryId, Pageable pageable);
}
