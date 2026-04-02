package com.example.web;

import com.example.dao.ArticleRepository;
import com.example.dao.CategoryRepository;
import com.example.entities.Article;
import com.example.utils.SessionUtils;
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


@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String REDIRECTION = "redirect:/index";
    private static final String CATEGORIES = "listCategories";

    /**
     * Displays the paginated list of articles, with optional keyword search
     * and category filtering
     *
     * @param model  the model used to pass attributes to the view
     * @param page   the page index to display
     * @param kw   the keyword to search within article descriptions
     * @param categoryId the ID of the category to filter by (optional)
     * @return the name of the articles list view template
     */
    @GetMapping("/index")
    public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name="keyword", defaultValue = "")String kw,
                                     @RequestParam(name= "categoryId", required = false) Long categoryId) {
        Page<Article> articles;

        if (categoryId != null) {
            articles = articleRepository.findByCategoryIdAndDeletedFalse(categoryId,PageRequest.of(page,5));
            model.addAttribute("currentCategory",categoryId);
        } else {
            articles = articleRepository.findByModelContainsAndDeletedFalse(kw,PageRequest.of(page,5));
            model.addAttribute("currentCategory",null);
        }

        model.addAttribute("listArticle", articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        return "articles";
    }

    //---------------------méthode pour supprimer un article ----------------------

    /**
     * Deletes an article by its ID
     *
     * @param id  the ID of the article to delete
     * @param page the current pagination page, used to preserve state on redirect
     * @param keyword the current search keyword, used to preserve state on redirect
     * @param session the current HTTP session, used to verify admin access
     * @return a redirect to /index, regardless of outcome
     */
    @GetMapping("/delete")
    public String delete(Long id, int page,String keyword,HttpSession session) {
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;
        articleRepository.findById(id).ifPresent(article -> {
            article.setDeleted(true);
            articleRepository.save(article);
        });
        return REDIRECTION;
    }

    //------------------formulaire d'ajout d'article-------------------------------

    /**
     * Displays the article creation form
     *
     * @param model   the Spring model used to pass attributes to the view
     * @param session the current HTTP session, used to verify admin access
     * @return the name of the article form view template, or a redirect
     *         to /index if the user is not an admin
     */
    @GetMapping("/formArticle")
    public String formArticle(Model model, HttpSession session) {
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;
        model.addAttribute("article", new Article());
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        return "formArticle";
    }

    //------------------méthode pour mettre à jour un article---------------------------

    /**
     * Displays the article editing form pre-populated with an existing article's data
     *
     * @param model the Spring model used to pass attributes to the view
     * @param id  the ID of the article to edit
     * @param session the current HTTP session, used to verify admin access
     * @return the name of the edit article view template, or a redirect
     *         to /index if the user is not an admin
     * @throws RuntimeException if no article is found for the given id
     */
    @GetMapping("/editArticle")
    public String editArticle(Model model, Long id, HttpSession session) {
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;
        Article article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article introuvable"));
        model.addAttribute("article", article);
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        return "editArticle";
    }

    //-----------------------------méthode pour sauvegarder un article valide------------------------

    /**
     * Saves a new or updated Article to the database
     *
     * If an id is provided, the existing article is loaded and its
     * fields are updated otherwise, a new article is created
     *
     * @param article  the Article object bound and validated
     * @param bindingResult the result of validation check on article
     * @param categoryId the ID of the category to assign to the article (optional)
     * @param id the ID of the article to update (optional)
     * @param session the current HTTP session, used to verify admin access
     * @param model the Spring model, used to repopulate the form on validation errors
     * @return a redirect /index on success, the form view on validation
     *         errors
     */
    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult bindingResult,
                       @RequestParam(name = "categoryId", required = false) Long categoryId,
                       @RequestParam(name = "id", required = false) Long id,
                       HttpSession session,Model model) {
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;
        if (bindingResult.hasErrors()) {
            model.addAttribute(CATEGORIES, categoryRepository.findAll());
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
        return REDIRECTION;
    }
}
