package com.example.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.example.entity.User;
import com.example.repository.UserRepository;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final UserRepository repo;

    public TestController(UserRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String health() {
        return "BSHTAXI IS RUNNING OK 🚀";
    }

    @PostMapping("/user")
    public User save(@RequestBody User user) {
        return repo.save(user);
    }

    @GetMapping("/users")
    public List<User> all() {
        return repo.findAll();
    }
}
