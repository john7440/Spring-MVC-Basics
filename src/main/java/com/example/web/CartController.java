package com.example.web;

import com.example.dao.ArticleRepository;
import com.example.dao.CartItemRepository;
import com.example.dao.CartRepository;
import com.example.entities.AppUser;
import com.example.entities.Cart;
import com.example.entities.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ArticleRepository articleRepository;

    //-------------------------méthode pour voir le panier----------------
    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        AppUser currentUser = (AppUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(currentUser);
                    return cartRepository.save(newCart);
                });
        model.addAttribute("cart", cart);
        return "cart";
    }
}
