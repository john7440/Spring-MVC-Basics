package com.example.web;

import com.example.dao.ArticleRepository;
import com.example.dao.OrderItemRepository;
import com.example.dao.OrderRepository;
import com.example.entities.AppUser;
import com.example.entities.Article;
import com.example.entities.Order;
import com.example.entities.OrderItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Places a new order for the currently authenticated user
     *
     * Returns 401 Unauthorized if no user is found
     * Returns 500 Internal Server Error if an article ID referenced
     * in the cart does not exist in the database
     *
     * @param items   a list of CartItemDTO objects representing the cart,
     *                each containing an article ID and a quantity
     * @param session the current HTTP session, used to retrieve the authenticated user
     * @return OK on success, or 401 Unauthorized if the user
     *         is not logged in
     */
    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody List<CartItemDTO> items,
                                        HttpSession session) {
        AppUser currentUser = (AppUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }

        Order order = new Order();
        order.setUser(currentUser);
        order.setDate(LocalDateTime.now());

        List<OrderItem> orderItems = items.stream().map(dto -> {
            Article article = articleRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Article introuvable"));
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setArticle(article);
            oi.setQuantity(dto.getQuantity());
            oi.setPriceAtOrder(article.getPrice());
            return oi;
        }).toList();

        double total = orderItems.stream()
                .mapToDouble(oi -> oi.getPriceAtOrder() * oi.getQuantity()).sum();

        order.setTotal(total);
        order.setItems(orderItems);
        orderRepository.save(order);

        return ResponseEntity.ok().build();
    }
}
