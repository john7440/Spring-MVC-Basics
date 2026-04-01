package com.example.web;

import com.example.dao.AppUserRepository;
import com.example.entities.AppUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private AppUserRepository userRepository;

    //-------------affichage formulaire de connexion -------------------

    /**
     * Mapping to the login Form
     * @return
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //-----------------méthode pour traiter la connexion--------------

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<AppUser> user = userRepository.findByUsername(username);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("currentUser", user.get());
            if (user.get().getRole().equals("ADMIN")) {
                return "redirect:/index";
            }
            return "redirect:/index";
        }
        model.addAttribute("error", "Identifiants incorrect");
        return "login";
    }

    //----------------------------inscription-----------------------------------------

    /**
     *  Mapping to the register form
     * @return
     */
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           HttpSession session) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "redirect:/register?error=exists";
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");
        userRepository.save(user);

        session.setAttribute("currentUser", user);
        return "redirect:/index";
    }

}
