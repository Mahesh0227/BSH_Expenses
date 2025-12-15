package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.AuthService;
import com.example.entity.User;

import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("ok", false, "message", "email and password are required"));
        }

        boolean created = authService.register(email.trim().toLowerCase(), password);

        if (!created) {
            return ResponseEntity.badRequest()
                    .body(Map.of("ok", false, "message", "User already exists"));
        }

        return ResponseEntity.status(201)
                .body(Map.of("ok", true, "message", "Registration Successful"));
    }

    // LOGIN  ✅ SESSION CREATED HERE
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req,
                                   HttpSession session) {

        String email = req.get("email");
        String password = req.get("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("ok", false, "message", "email and password are required"));
        }

        User user = authService.login(email.trim().toLowerCase(), password);

        if (user == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("ok", false, "message", "Invalid Credentials"));
        }

        // ✅ STORE USER IN SESSION
        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("USER_EMAIL", user.getEmail());

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("email", user.getEmail());

        return ResponseEntity.ok(Map.of(
                "ok", true,
                "message", "Login Successful",
                "data", data
        ));
    }

    // LOGOUT
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of(
                "ok", true,
                "message", "Logged out successfully"
        ));
    }
}
