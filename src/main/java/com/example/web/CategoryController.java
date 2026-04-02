package com.example.web;

import com.example.dao.CategoryRepository;
import com.example.entities.Category;
import com.example.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String REDIRECTION = "redirect:/index";

    //-----------------------la liste des catégories------------------------

    /**
     * Displays the category management page
     *
     * @param model   the model used to pass attributes to the view
     * @param session the current HTTP session, used to verify admin access
     * @return the name of the categories view template, or a redirect
     *         to /index if the user is not an admin
     */
    @GetMapping("/admin/categories")
    public String categories(Model model, HttpSession session){
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("newCategory", new Category());
        return "categories";
    }

    //------méthode pour ajouter ou modifier une catégorie ------------------------

    /**
     * Creates a new category or updates an existing one
     *
     * @param id  the ID of the category to update; if null, a new category is created
     * @param name the name to assign to the category
     * @param session the current HTTP session, used to verify admin access
     * @return a redirect to /admin/categories on success, or to
     *         /index if the user is not an admin
     */
    @PostMapping("/admin/saveCategory")
    public String saveCategory(@RequestParam(required = false) Long id,
                               @RequestParam String name,
                               HttpSession session){
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;

        Category category;
        if (id != null){
            category = categoryRepository.findById(id)
                    .orElse(new Category());
        } else  {
            category = new Category();
        }
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    //---------------méthode pour supprimer une catégorie----------------

    /**
     * Deletes a category by its id
     *
     * @param id the ID of the category to delete
     * @param session the current HTTP session, used to verify admin access
     * @return a redirect to /admin/categories on success, or to
     *         /index if the user is not an admin
     */
    @GetMapping("/admin/deleteCategory")
    public String deleteCategory(@RequestParam Long id, HttpSession session){
        if(SessionUtils.isNotAdmin(session)) return  REDIRECTION;
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories";
    }

    //----------------méthode utilitaire pour vérifier si l'utilisateur actuel est admin----

}
