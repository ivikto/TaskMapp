package com.taskm.TaskMapp.controllers;

import com.taskm.TaskMapp.model.Task;
import com.taskm.TaskMapp.model.User;
import com.taskm.TaskMapp.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
    private static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Возвращает страницу регистрации
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.registerUser(user);
        logger.info("Пользователь {} успешно создан", user.getUsername());

        return "redirect:/login"; // Перенаправляет на страницу входа
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Возвращает страницу регистрации
    }
}