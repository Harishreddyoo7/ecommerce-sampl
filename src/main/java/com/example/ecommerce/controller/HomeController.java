package com.example.ecommerce.controller;

import com.example.ecommerce.repo.ProductRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductRepo products;

    public HomeController(ProductRepo products) {
        this.products = products;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("products", products.findAll());
        model.addAttribute("userEmail", user.getUsername());
        return "home";
    }
}
