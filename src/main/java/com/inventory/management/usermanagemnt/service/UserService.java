package com.inventory.management.usermanagemnt.service;

import com.inventory.management.usermanagemnt.model.*;
import com.inventory.management.usermanagemnt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String username, String password, String email, String role) {
        User user;
        if ("ADMIN".equalsIgnoreCase(role)) {
            user = new AdminUser();
        } else {
            user = new StaffUser();
        }
        user.setUsername(username);
        user.setPassword(password); // In production, encode with BCrypt
        user.setEmail(email);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> searchByUsername(String keyword) {
        return userRepository.findByUsernameContainingIgnoreCase(keyword);
    }

    public String loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {

                return user.login();
            }
        }
        return "Invalid username or password.";
    }

    public User updateUser(Long id, String email, String password, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}