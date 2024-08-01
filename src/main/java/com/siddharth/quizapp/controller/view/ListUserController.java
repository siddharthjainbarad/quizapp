package com.siddharth.quizapp.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListUserController {

    @GetMapping("/users")
    public String signup() {
        return "users";
    }
}