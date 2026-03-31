package com.example.web;

import com.example.dao.AppUserRepository;
import com.example.dao.ArticleRepository;
import com.example.dao.CartItemRepository;
import com.example.dao.CartRepository;
import com.example.entities.AppUser;
import com.example.entities.Article;
import com.example.entities.Cart;
import com.example.entities.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    //-------------------méthode ajouter un article au panier----------------------
    @GetMapping("/cart/add")
    public String addToCart(@RequestParam Long articleId,
                            @RequestParam(defaultValue = "1") int quantity,HttpSession session) {
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
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("article non trouvé"));

        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getArticle().getId().equals(articleId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setArticle(article);
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
        return "redirect:/cart";
    }


    //TODO supprimer un article du panier

    //TODO vider le panier
}
