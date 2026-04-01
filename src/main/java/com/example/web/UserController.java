package com.example.web;

import com.example.dao.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private AppUserRepository userRepository;

    //-------------affichage formulaire de connexion -------------------
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
