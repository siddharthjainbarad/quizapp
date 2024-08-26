package com.siddharth.quizapp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.siddharth.quizapp.security.CustomUserDetails;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDetails", new CustomUserDetails());
        return "/public/login";
    }
}  