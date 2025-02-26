package com.pms.controller;

import com.pms.entity.Claim;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClaimControllerHome {

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("claim", new Claim()); // Ensure claim is available for Thymeleaf
        return "index";
    }
}
