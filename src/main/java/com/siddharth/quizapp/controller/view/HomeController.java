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
        model.addAttribute("username", username);
        return "/private/home";
    }

    @GetMapping("/update-profile")
    public String updateProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        return "/private/update-profile";
    }

    @GetMapping("/create-quiz")
    public String createQuiz() {
        return "/private/create-quiz";
    }

    @GetMapping("/take-quiz")
    public String takeeQuiz() {
        return "/private/take-quiz";
    }

    @GetMapping("/list-user")
    public String signup() {
        return "/private/list-user";
    }

    @GetMapping("/update-quiz")
    public String updateQuiz() {
        return "/private/update-quiz";
    }
}
