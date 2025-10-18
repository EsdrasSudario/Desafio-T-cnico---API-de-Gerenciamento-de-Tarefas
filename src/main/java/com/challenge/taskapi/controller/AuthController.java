package com.challenge.taskapi.controller;

import com.challenge.taskapi.dto.LoginRequest;
import com.challenge.taskapi.dto.SignupRequest;
import com.challenge.taskapi.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
    	ResponseEntity<?> response = authService.register(request);
    	return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    	ResponseEntity<?> response = authService.login(request);
    	return response;
    }
}
