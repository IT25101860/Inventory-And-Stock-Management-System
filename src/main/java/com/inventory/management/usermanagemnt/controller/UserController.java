package com.inventory.management.usermanagemnt.controller;

import com.inventory.management.usermanagemnt.model.User;
import com.inventory.management.usermanagemnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "users/register"; // templates/users/register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam String role,
                               Model model) {
        userService.registerUser(username, password, email, role);
        model.addAttribute("message", "User registered successfully!");
        return "users/register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "users/login"; // templates/users/login.html
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        String result = userService.loginUser(username, password);
        model.addAttribute("loginResult", result);
        return "users/login";
    }

    @GetMapping("/list")
    public String listUsers(@RequestParam(required = false) String keyword, Model model) {
        List<User> users;
        if (keyword != null && !keyword.isEmpty()) {
            users = userService.searchByUsername(keyword);
        } else {
            users = userService.getAllUsers();
        }
        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users/list"; // templates/users/list.html
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role,
                             Model model) {
        userService.updateUser(id, email, password, role);
        return "redirect:/users/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users/list";
    }
}
