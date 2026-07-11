package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.converter.UserAuthConverter;
import com.ecommerce.authservice.dto.AuthResponse;
import com.ecommerce.authservice.dto.LoginRequest;
import com.ecommerce.authservice.dto.RegisterRequest;
import com.ecommerce.authservice.dto.UserResponse;
import com.ecommerce.authservice.model.User;
import com.ecommerce.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        final User user = authService.register(UserAuthConverter.convertToModel(request));
        return UserResponse.fromUser(user);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return new AuthResponse(authService.login(request));
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal String userId) {
        return UserResponse.fromUser(authService.getById(Long.valueOf(userId)));
    }
}
