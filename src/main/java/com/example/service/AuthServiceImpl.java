package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean register(String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) return false;
        User u = new User();
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(u);
        return true;
    }

    @Override
    public User login(String email, String rawPassword) {
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) return null;
        User u = opt.get();
        if (passwordEncoder.matches(rawPassword, u.getPassword())) {
            return u;
        }
        return null;
    }
}
