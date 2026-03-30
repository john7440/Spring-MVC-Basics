package com.example.web;

import com.example.dao.ArticleRepository;
import com.example.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/index")
    public String index(Model model) {
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("listArticle", articles);
        return "articles";
    }
}
