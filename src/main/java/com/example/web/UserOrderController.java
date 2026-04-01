package com.example.web;

import com.example.dao.OrderRepository;
import com.example.entities.AppUser;
import com.example.entities.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserOrderController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Displays the orders
     * @return the name of the orders template
     */
    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {
        AppUser currentUser = (AppUser) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/login";

        List<Order> orders = orderRepository.findByUserId(currentUser.getId());
        model.addAttribute("orders", orders);
        return "orders";
    }

    /**
     * Displays the order confirmation
     * @return the name of the order confirmation template
     */
    @GetMapping("/orderConfirmation")
    public String orderConfirmation() {
        return "orderConfirmation";
    }
}
