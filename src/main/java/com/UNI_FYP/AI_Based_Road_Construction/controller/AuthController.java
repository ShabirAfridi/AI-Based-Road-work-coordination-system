package com.UNI_FYP.AI_Based_Road_Construction.controller;

import com.UNI_FYP.AI_Based_Road_Construction.dto.ApiResponse;
import com.UNI_FYP.AI_Based_Road_Construction.dto.AuthRequest;
import com.UNI_FYP.AI_Based_Road_Construction.dto.AuthResponse;
import com.UNI_FYP.AI_Based_Road_Construction.dto.RegisterRequest;
import com.UNI_FYP.AI_Based_Road_Construction.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
// All endpoints in this controller are PUBLIC
// SecurityConfig permits /api/auth/** without a token
public class AuthController {
    private final AuthService authService;

    // POST /api/auth/register
    // Body: { fullName, email, password, role, departmentId }
    // Returns: { token, email, fullName, role, departmentId }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthResponse authResponse = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", authResponse));
    }
    // POST /api/auth/login
    // Body: { email, password }
    // Returns: { token, email, fullName, role, departmentId }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody AuthRequest request
    ) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", authResponse));
    }
}