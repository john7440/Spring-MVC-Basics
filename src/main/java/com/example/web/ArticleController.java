package com.example.web;

import com.example.dao.ArticleRepository;
import com.example.dao.CategoryRepository;
import com.example.entities.Article;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name="keyword", defaultValue = "")String kw,
                                     @RequestParam(name= "categoryId", required = false) Long categoryId) {
        Page<Article> articles;

        if (categoryId != null) {
            articles = articleRepository.findByCategoryId(categoryId,PageRequest.of(page,5));
            model.addAttribute("currentCategory",categoryId);
        } else {
            articles = articleRepository.findByDescriptionContains(kw,PageRequest.of(page,5));
            model.addAttribute("currentCategory",null);
        }

        model.addAttribute("listArticle", articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        model.addAttribute("listCategories", categoryRepository.findAll());
        return "articles";
    }

    @GetMapping("/delete")
    public String delete(Long id, int page,String keyword) {
        articleRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/formArticle")
    public String formArticle(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("listCategories", categoryRepository.findAll());
        return "formArticle";
    }

    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult bindingResult,
                       @RequestParam(name = "categoryId", required = false) Long categoryId,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listCategories", categoryRepository.findAll());
            return "formArticle";
        }
        if (categoryId != null) {
            categoryRepository.findById(categoryId).ifPresent(article::setCategory);
        }
        articleRepository.save(article);
        return "redirect:/index";
    }
}
