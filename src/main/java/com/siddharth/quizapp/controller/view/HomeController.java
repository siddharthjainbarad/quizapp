package com.siddharth.quizapp.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.model.UserProfile;
import com.siddharth.quizapp.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

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
        User user = userService.findByUsername(username);
        model.addAttribute("username", username);
        if (user != null) {
            model.addAttribute("email", user.getEmail() != null ? user.getEmail() : "");

            UserProfile userProfile = user.getUserProfile();
            if (userProfile != null) {
                model.addAttribute("firstName", userProfile.getFirstName() != null ? userProfile.getFirstName() : "");
                model.addAttribute("lastName", userProfile.getLastName() != null ? userProfile.getLastName() : "");
                model.addAttribute("dob", userProfile.getDateOfBirth() != null ? userProfile.getDateOfBirth().toString() : "");
            } 
        } else {
            model.addAttribute("email", "");
            model.addAttribute("firstName", "");
            model.addAttribute("lastName", "");
            model.addAttribute("dob", "");
        }
        return "/private/update-profile";
    }

    @GetMapping("/create-quiz")
    public String createQuiz(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        return "/private/create-quiz";
    }

    @GetMapping("/take-quiz")
    public String takeeQuiz(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        return "/private/take-quiz";
    }

    @GetMapping("/list-user")
    public String signup() {
        return "/private/list-user";
    }

    @GetMapping("/update-quiz")
    public String updateQuiz(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        return "/private/update-quiz";
    }

    @GetMapping("/user-profile")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("username", username);
        return "/private/user-profile";
    }
}
