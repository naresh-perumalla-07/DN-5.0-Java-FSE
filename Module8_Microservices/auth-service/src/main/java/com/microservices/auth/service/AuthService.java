package com.microservices.auth.service;

import com.microservices.auth.dto.AuthRequest;
import com.microservices.auth.dto.AuthResponse;
import com.microservices.auth.dto.RegisterRequest;
import com.microservices.auth.model.User;
import com.microservices.auth.repository.UserRepository;
import com.microservices.auth.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        String roles = (request.getRoles() != null && !request.getRoles().isBlank())
                ? request.getRoles() : "USER";

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                roles
        );
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return new AuthResponse(token, user.getUsername(), user.getRoles());
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return new AuthResponse(token, user.getUsername(), user.getRoles());
    }
}
