package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final UserRepo users;
    private final PasswordEncoder encoder;

    public AuthController(UserRepo users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/register")
    public String register(Model model) {
        model.addAttribute("userForm", new RegisterForm());
        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String doRegister(@ModelAttribute("userForm") @Valid RegisterForm form, BindingResult result, Model model) {
        if (result.hasErrors()) return "auth/register";
        if (users.findByEmail(form.getEmail()).isPresent()) {
            result.rejectValue("email", "exists", "Email already in use");
            return "auth/register";
        }
        User u = new User();
        u.setEmail(form.getEmail());
        u.setPasswordHash(encoder.encode(form.getPassword()));
        users.save(u);
        return "redirect:/auth/login?registered";
    }

    public static class RegisterForm {
        @javax.validation.constraints.Email
        @javax.validation.constraints.NotBlank
        private String email;
        @javax.validation.constraints.NotBlank
        private String password;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
