package com.example.web;

import com.example.entities.AppUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        AppUser currentUser = (AppUser) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        return "cart";
    }
}
