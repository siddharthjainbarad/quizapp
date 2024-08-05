package com.siddharth.quizapp.controller.view;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username",username);
        return "home";
    }

    @GetMapping("/create-quiz")
    public String createQuiz() {
        return "create-quiz";
    }

    @GetMapping("/list-user")
    public String signup() {
        return "/list-user";
    }

    @GetMapping("/update-quiz")
    public String updateQuiz() {
        return "/update-quiz";
    }
}