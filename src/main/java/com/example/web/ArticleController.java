package com.example.web;

import com.example.dao.ArticleRepository;
import com.example.dao.CategoryRepository;
import com.example.entities.AppUser;
import com.example.entities.Article;
import jakarta.servlet.http.HttpSession;
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

    private boolean isAdmin(HttpSession session){
        AppUser appUser = (AppUser)session.getAttribute("currentUser");
        return appUser != null && appUser.getRole().equals("ADMIN");
    }

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

    //---------------------méthode pour supprimer un article ----------------------
    @GetMapping("/delete")
    public String delete(Long id, int page,String keyword,HttpSession session) {
        if (!isAdmin(session)) return "redirect:/index";
        articleRepository.deleteById(id);
        return "redirect:/index";
    }

    //------------------formulaire d'ajout d'article-------------------------------
    @GetMapping("/formArticle")
    public String formArticle(Model model, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/index";
        model.addAttribute("article", new Article());
        model.addAttribute("listCategories", categoryRepository.findAll());
        return "formArticle";
    }

    //------------------méthode pour mettre à jour un article---------------------------
    @GetMapping("/editArticle")
    public String editArticle(Model model, Long id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/index";
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article introuvable"));
        model.addAttribute("article", article);
        model.addAttribute("listCategories", categoryRepository.findAll());
        return "editArticle";
    }

    //-----------------------------méthode pour sauvegarder un article valide------------------------
    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult bindingResult,
                       @RequestParam(name = "categoryId", required = false) Long categoryId,
                       @RequestParam(name = "id", required = false) Long id,
                       HttpSession session,Model model) {
        if (!isAdmin(session)) return "redirect:/index";
        if (bindingResult.hasErrors()) {
            model.addAttribute("listCategories", categoryRepository.findAll());
            return "formArticle";
        }

        Article toSave;

        if (id != null) {
            toSave = articleRepository.findById(id).orElse(new Article());
        } else {
            toSave = new Article();
        }

        toSave.setBrand(article.getBrand());
        toSave.setModel(article.getModel());
        toSave.setDescription(article.getDescription());
        toSave.setPrice(article.getPrice());

        if (categoryId != null) {
            categoryRepository.findById(categoryId).ifPresent(toSave::setCategory);
        }

        articleRepository.save(toSave);
        return "redirect:/index";
    }
}
