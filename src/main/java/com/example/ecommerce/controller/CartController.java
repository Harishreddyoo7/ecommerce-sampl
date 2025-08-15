package com.example.ecommerce.controller;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.CartItemRepo;
import com.example.ecommerce.repo.ProductRepo;
import com.example.ecommerce.repo.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CartController {

    private final CartItemRepo cartItems;
    private final ProductRepo products;
    private final UserRepo users;

    public CartController(CartItemRepo cartItems, ProductRepo products, UserRepo users) {
        this.cartItems = cartItems;
        this.products = products;
        this.users = users;
    }

    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal UserDetails user, Model model) {
        User u = users.findByEmail(user.getUsername()).orElseThrow();
        List<CartItem> items = cartItems.findByUser(u);
        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/cart/add/{productId}")
    public String add(@PathVariable Long productId, @AuthenticationPrincipal UserDetails user) {
        User u = users.findByEmail(user.getUsername()).orElseThrow();
        Product p = products.findById(productId).orElseThrow();
        CartItem ci = new CartItem();
        ci.setUser(u);
        ci.setProduct(p);
        ci.setQuantity(1);
        cartItems.save(ci);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{itemId}")
    public String remove(@PathVariable Long itemId, @AuthenticationPrincipal UserDetails user) {
        cartItems.deleteById(itemId);
        return "redirect:/cart";
    }
}
