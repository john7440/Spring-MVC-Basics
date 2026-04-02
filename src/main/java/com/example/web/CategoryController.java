package com.example.web;

import com.example.dao.CategoryRepository;
import com.example.entities.AppUser;
import com.example.entities.Category;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    //-----------------------la liste des catégories------------------------

    @GetMapping("/admin/categories")
    public String categories(Model model, HttpSession session){
        if(!isAdmin(session)) return "redirect:/index";
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("newCategory", new Category());
        return "categories";
    }

    //----------------méthode utilitaire pour vérifier si l'utilisateur actuel est admin----

    /**
     * Checks whether the currently authenticated user has the "ADMIN" role
     *
     * @param session the current HTTP session
     * @return true if the session holds a non-null user with role "ADMIN",
     *         false otherwise
     */
    private boolean isAdmin(HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("currentUser");
        return user != null && user.getRole().equals("ADMIN");
    }
}
