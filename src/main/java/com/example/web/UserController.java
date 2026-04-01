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

/**
 * Controller responsible for handling user authentication operations like
 * login, registration and logout
 */
@Controller
public class UserController {

    @Autowired
    private AppUserRepository userRepository;

    //-------------affichage formulaire de connexion -------------------

    /**
     * Displays the login form
     * @return the name of the login template
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    //-----------------méthode pour traiter la connexion--------------

    /**
     * Processes the login form submission
     * @param username the username submitted via the login form
     * @param password the plain-text password submitted via the login form
     * @param session  the current HTTP session, used to store the authenticated user
     * @param model the Spring MVC model, used to pass error messages to the view
     * @return  redirect to /index on success/failure
     */
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
     * Displays the registration form
     * @return the name of the register template
     */
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    /**
     * Processes the registration form submission
     *
     * @param username the desired username submitted via the registration form
     * @param password the plain-text password submitted via the registration form
     * @param session  the current HTTP session, used to store the newly registered user
     * @return a redirect to /register?error=exists if the username is already taken,
     *         or a redirect to /index on successful registration
     */
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

    //-------------------méthode de déconnexion ------------------------------

    /**
     * Logs out the current user by invalidating the HTTP session
     *
     * @param session the current HTTP session to invalidate
     * @return a redirect to /index
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

}
